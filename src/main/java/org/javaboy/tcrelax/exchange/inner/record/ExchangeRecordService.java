package org.javaboy.tcrelax.exchange.inner.record;

import org.javaboy.tcrelax.common.PageResult;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;

import java.util.List;

/**
 * @author:majin.wj 兑换奖励记录服务
 */
public interface ExchangeRecordService {


    PageResult<List<AwardRecordDTO>> queryUserRecord(String uid, String scene, Integer page, Integer pageSize);


    void addUserRecord(AwardRecordDTO record);


}
