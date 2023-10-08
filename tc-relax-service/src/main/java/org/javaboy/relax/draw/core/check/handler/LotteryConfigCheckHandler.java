package org.javaboy.relax.draw.core.check.handler;

import com.alibaba.fastjson.JSON;

import org.javaboy.relax.draw.core.config.DrawActivityConfigManager;
import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.DrawActivityConfigEntity;
import org.javaboy.relax.draw.enums.LotteryOptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author:majin.wj
 */
@Order(2)
@Component
public class LotteryConfigCheckHandler implements LotteryCheckHandler {

    @Autowired
    private DrawActivityConfigManager activityConfigManager;

    /**
     * 抽奖活动有消校验
     *
     * @param request
     * @param context
     * @return
     */
    @Override
    public boolean check(DrawRequest request, LotteryContext context) {
        DrawActivityConfigEntity activityConfig = activityConfigManager.getActivityConfigEntity(request.getActivityId());
        Assert.notNull(activityConfig, "不存在该活动");
        long nowTime = new Date().getTime();
        Assert.isTrue(nowTime <= activityConfig.getStartTime().getTime(), "activity not action");
        Assert.isTrue(nowTime >= activityConfig.getEndTime().getTime(), "activity has ended");
        // 深拷贝一份配置，因为后面抽奖需要对集合进行操作
        DrawActivityConfigEntity deepClonedActivityConfig = JSON.parseObject(JSON.toJSONString(activityConfig), DrawActivityConfigEntity.class);
        context.setDrawActivityConfigEntity(deepClonedActivityConfig);
        return true;
    }

    @Override
    public boolean supportOption(String scene) {
        return LotteryOptionEnum.DRAW.isSameOption(scene) || LotteryOptionEnum.PREVIEW.isSameOption(scene);
    }
}
