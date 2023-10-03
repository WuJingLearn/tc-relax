package org.javaboy.tcrelax.exchange.inner.record;

import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;
import org.javaboy.tcrelax.exchange.inner.repository.ExchangeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:majin.wj
 */
@Component
public class ExchangeRecordServiceImpl implements ExchangeRecordService {

    @Autowired
    private ExchangeRecordRepository recordRepository;

    @Override
    public List<AwardRecordDTO> queryUserRecord(String uid, String scene) {
        return recordRepository.queryUserRecord(uid,scene);
    }

    @Override
    public void addUserRecord(AwardRecordDTO record) {
        recordRepository.addUserRecord(record);
    }
}
