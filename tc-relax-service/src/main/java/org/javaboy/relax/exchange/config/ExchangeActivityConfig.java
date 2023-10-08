package org.javaboy.relax.exchange.config;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:majin.wj
 * 兑换活动配置
 */
@Data
public class ExchangeActivityConfig {

    private String scene;

    private String activityName;

    private Date startTime;

    private Date endTime;

    /**
     * 权益配置
     */
    private List<ExchangeBenefitConfig> benefitConfigList;


    /**
     * 活动范围疲劳度控制,比如只能兑换两个商品
     */
    private ExchangeFatigueConfig fatigueConfig;

}
