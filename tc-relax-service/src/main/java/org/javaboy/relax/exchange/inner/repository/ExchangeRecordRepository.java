package org.javaboy.relax.exchange.inner.repository;

import org.javaboy.relax.common.PageResult;
import org.javaboy.relax.exchange.dto.AwardRecordDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface ExchangeRecordRepository {

    PageResult<List<AwardRecordDTO>> queryUserRecord(String uid, String scene, Integer page, Integer pageSize);

    void addUserRecord(AwardRecordDTO recordDTO);

}
