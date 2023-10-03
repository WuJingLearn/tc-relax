package org.javaboy.tcrelax.exchange.inner.repository;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Component
public class ExchangeRecordMemoryRepository implements ExchangeRecordRepository {


    public static final String USER_EXCHANGE_RECORD_KEY = "exchange_%s_%s";

    @Autowired
    private Jedis jedis;

    @Override
    public List<AwardRecordDTO> queryUserRecord(String uid, String scene) {
        List<String> awardRecords = jedis.lrange(String.format(USER_EXCHANGE_RECORD_KEY, scene, uid), 0, -1);
        if (CollectionUtils.isNotEmpty(awardRecords)) {
            return awardRecords.stream().map(str -> {
                return JSON.parseObject(str, AwardRecordDTO.class);
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void addUserRecord(AwardRecordDTO recordDTO) {
        String awardRecord = JSON.toJSONString(recordDTO);
        jedis.lpush(String.format(USER_EXCHANGE_RECORD_KEY,recordDTO.getUid(),recordDTO.getScene()),awardRecord);
    }
}
