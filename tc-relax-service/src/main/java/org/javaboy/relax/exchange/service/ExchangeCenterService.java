package org.javaboy.relax.exchange.service;

import org.javaboy.relax.common.PageResult;
import org.javaboy.relax.common.TcResult;
import org.javaboy.relax.exchange.ExchangeRequest;
import org.javaboy.relax.exchange.dto.ActivityDetailDTO;
import org.javaboy.relax.exchange.dto.AwardRecordDTO;
import org.javaboy.relax.exchange.dto.BenefitDTO;

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
