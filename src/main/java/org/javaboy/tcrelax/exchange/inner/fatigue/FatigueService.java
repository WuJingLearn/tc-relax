package org.javaboy.tcrelax.exchange.inner.fatigue;

import org.javaboy.tcrelax.exchange.ExchangeContext;

/**
 * @author:majin.wj
 */
public interface FatigueService {

    /**
     * 校验活动疲劳度,可以配置一个活动，只能兑换几次
     * @param exchangeContext
     * @return
     */
    void checkActivityFatigue(ExchangeContext exchangeContext);

    /**
     * 交易具体一个权益的疲劳度，一个商品可以兑换几次
     * @param exchangeContext
     * @return
     */
    void checkBenefitFatigue(ExchangeContext exchangeContext);


    /**
     * 记录用户疲劳度
     * @param exchangeContext
     */
    void recordUserFatigue(ExchangeContext exchangeContext);

}
