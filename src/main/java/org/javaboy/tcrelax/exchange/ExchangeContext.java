package org.javaboy.tcrelax.exchange;

import lombok.Data;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeBenefitConfig;

/**
 * @author:majin.wj
 */
@Data
public class ExchangeContext {

    private String userId;

    private String scene;

    private ExchangeRequest request;

    /**
     * 扣减用户资产结果
     */
    private boolean deductUserAssetResult;

    /**
     * 扣减库存结果
     */
    private boolean deductInventoryResult;

    /**
     * 兑换活动配置
     */
    private ExchangeActivityConfig activityConfig;


    /**
     * 本次兑换的商品
     */
    private ExchangeBenefitConfig benefitConfig;



}
