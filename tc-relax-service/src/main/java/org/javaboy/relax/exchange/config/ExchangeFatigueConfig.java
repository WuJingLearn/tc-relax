package org.javaboy.relax.exchange.config;

import lombok.Data;

/**
 * @author:majin.wj 疲劳度配置；用于控制商品兑换的限领策略
 */
@Data
public class ExchangeFatigueConfig {


    private String fatigueType;
    private Integer amount;



}
