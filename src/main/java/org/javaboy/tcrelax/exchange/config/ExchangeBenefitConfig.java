package org.javaboy.tcrelax.exchange.config;

import lombok.Data;

/**
 * @author:majin.wj
 *
 */
@Data
public class ExchangeBenefitConfig {

    private String benefitCode;
    private String url;

    /**
     * 是否使用库存
     */
    private boolean useInventory;

    /**
     * 库存配置，用户单个商品
     */
    private ExchangeInventoryConfig inventoryConfig;


    /**
     * 疲劳度控制，用于单个商品
     */
    private ExchangeFatigueConfig fatigueConfig;


    /**
     * 兑换消耗配置，用于单个商品
     */
    private ExchangeCostConfig costConfig;




}
