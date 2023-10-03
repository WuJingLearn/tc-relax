package org.javaboy.tcrelax.exchange.inner.repository;

import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface ExchangeRecordRepository {

    List<AwardRecordDTO> queryUserRecord(String uid, String scene);

    void addUserRecord(AwardRecordDTO recordDTO);

}
