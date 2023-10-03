package org.javaboy.tcrelax.exchange.inner.cost;

import org.javaboy.tcrelax.exchange.ExchangeContext;

/**
 * @author:majin.wj
 * 兑换商品需要满足什么条件
 */
public interface CostBenefitService {


    /**
     * 校验用户资产
     * @param exchangeContext
     * @return
     */
    void checkUserAsset(ExchangeContext exchangeContext);

    /**
     * 扣减用户资产
     * @param exchangeRequest
     * @return
     */
    boolean deductUserAsset(ExchangeContext exchangeRequest);


    void increaseUserAsset(ExchangeContext exchangeRequest);



}
