package org.javaboy.tcrelax.exchange.inner.repository;

import org.javaboy.tcrelax.common.PageResult;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface ExchangeRecordRepository {

    PageResult<List<AwardRecordDTO>> queryUserRecord(String uid, String scene, Integer page, Integer pageSize);

    void addUserRecord(AwardRecordDTO recordDTO);

}
