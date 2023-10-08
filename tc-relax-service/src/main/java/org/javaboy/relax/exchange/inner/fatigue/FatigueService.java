package org.javaboy.relax.exchange.inner.fatigue;

import org.javaboy.relax.exchange.ExchangeContext;

/**
 * @author:majin.wj
 */
public interface FatigueService {

    /**
     * 校验活动疲劳度,可以配置一个活动，只能兑换几次
     *
     * @param exchangeContext
     * @return
     */
    void checkActivityFatigue(ExchangeContext exchangeContext);

    /**
     * 交易具体一个权益的疲劳度，一个商品可以兑换几次
     *
     * @param exchangeContext
     * @return
     */
    void checkBenefitFatigue(ExchangeContext exchangeContext);


    /**
     * 记录活动级别的疲劳度
     *
     * @param exchangeContext
     */
    void recordActivityFatigue(ExchangeContext exchangeContext);

    /**
     * 记录权益级别疲劳度
     *
     * @param exchangeContext
     */
    void recordBenefitFatigue(ExchangeContext exchangeContext);

}
