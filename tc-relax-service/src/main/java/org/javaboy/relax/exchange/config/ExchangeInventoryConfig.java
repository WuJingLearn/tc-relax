package org.javaboy.relax.exchange.config;


import lombok.Data;

/**
 * @author:majin.wj
 * 比如总库存100.每小时10个
 */
@Data
public class ExchangeInventoryConfig {

    /**
     * 库存策略
     */
    private String inventoryStrategy;

    /**
     * 活动期间总限量
     */
    private Integer totalAmount;

    /**
     * 每小时限量
     */
    private Integer hourAmount;

}
