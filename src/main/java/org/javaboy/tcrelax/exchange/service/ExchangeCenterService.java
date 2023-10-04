package org.javaboy.tcrelax.exchange.service;

import org.javaboy.tcrelax.common.PageResult;
import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.exchange.ExchangeRequest;
import org.javaboy.tcrelax.exchange.dto.ActivityDetailDTO;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;
import org.javaboy.tcrelax.exchange.dto.BenefitDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface ExchangeCenterService {


    /**
     * 兑换商品
     * @param request
     * @return
     */
    TcResult<BenefitDTO> exchange(ExchangeRequest request);


    /**
     * 预览活动列表
     * @param request
     */
    TcResult<ActivityDetailDTO> preview(ExchangeRequest request);


    TcResult<PageResult<List<AwardRecordDTO>>> queryExchangeRecord(String scene, String uid, Integer page, Integer pageSize);

}
