package org.javaboy.tcrelax.draw.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.draw.entity.DrawActivityConfigEntity;
import org.javaboy.tcrelax.draw.service.impl.DrawActivityService;
import org.javaboy.tcrelax.insfra.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author:majin.wj
 */
@Component
@Slf4j
public class DrawActivityConfigManager {

    private static final String DRAW_GROUP_ID = "defaultDrawGroup";
    private static final String DRAW_DATA_PREFIX = "drawActivity_%s";
    private static final Long expireSeconds = 60 * 60 * 24 * 30L;

    private Cache<String, DrawActivityConfigEntity> activityLocalCache =
            CacheBuilder.newBuilder().maximumSize(1024).expireAfterWrite(expireSeconds, TimeUnit.SECONDS).build();

    @Autowired
    private RedisClient redisClient;

    @Autowired(required = false)
    private ConfigService configService;

    @Autowired
    private DrawActivityService activityService;

    /**
     * 发布配置到配置中心
     *
     * @param activityId
     */
    public void publishDrawActivity(String activityId) {
        try {
            DrawActivityConfigEntity activityConfigEntity = activityService.getDrawActivityConfigEntity(activityId);
            String configInfo = JSON.toJSONString(activityConfigEntity);
            boolean success = configService.publishConfig(String.format(DRAW_DATA_PREFIX, activityId), DRAW_GROUP_ID, configInfo);
            if (success) {
                redisClient.put(activityId, configInfo, expireSeconds.intValue());
                activityLocalCache.put(activityId, activityConfigEntity);
            }
            log.info("活动配置发布 {},configInfo:{}", success ? "成功" : "失败", configInfo);
        } catch (Exception e) {
            throw new RuntimeException("活动配置发布失败", e);
        }
    }


    public DrawActivityConfigEntity getActivityConfigEntity(String activityId) {
        try {
            DrawActivityConfigEntity drawActivityConfigEntity = activityLocalCache.get(activityId, () -> {
                return JSON.parseObject((String) redisClient.get(activityId), DrawActivityConfigEntity.class);
            });
            if (drawActivityConfigEntity == null) {
                String config = configService.getConfig(String.format(DRAW_DATA_PREFIX, activityId), DRAW_GROUP_ID, 3000);
                if (config == null) {
                    redisClient.put(activityId, "$emptyActivity", expireSeconds.intValue());
                } else {
                    redisClient.put(activityId, config, expireSeconds.intValue());
                    activityLocalCache.put(activityId, JSON.parseObject(config, DrawActivityConfigEntity.class));
                }
            }
            return null;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

}
