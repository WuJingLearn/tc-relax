package org.javaboy.relax.exchange.inner.repository;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.javaboy.relax.common.PageResult;
import org.javaboy.relax.exchange.dto.AwardRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

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
    public PageResult<List<AwardRecordDTO>> queryUserRecord(String uid, String scene, Integer page, Integer pageSize) {
        PageResult<List<AwardRecordDTO>> pageResult = new PageResult<>();
        String recordKey = String.format(USER_EXCHANGE_RECORD_KEY, scene, uid);
        Long total = jedis.llen(recordKey);
        pageResult.setTotalSize(total);

        int start = (page - 1) * pageSize;
        int end = page * pageSize - 1;

        List<String> awardRecords = jedis.lrange(recordKey, start, end);
        pageResult.setCurPageSize(awardRecords.size());
        pageResult.setTotalPage(total / pageSize);
        if (CollectionUtils.isNotEmpty(awardRecords)) {
            List<AwardRecordDTO> awardRecordDTOS = awardRecords.stream().map(str -> {
                return JSON.parseObject(str, AwardRecordDTO.class);
            }).collect(Collectors.toList());
            pageResult.setData(awardRecordDTOS);
        }
        return pageResult;
    }

    @Override
    public void addUserRecord(AwardRecordDTO recordDTO) {
        String awardRecord = JSON.toJSONString(recordDTO);
        jedis.lpush(String.format(USER_EXCHANGE_RECORD_KEY, recordDTO.getUid(), recordDTO.getScene()), awardRecord);
    }
}
