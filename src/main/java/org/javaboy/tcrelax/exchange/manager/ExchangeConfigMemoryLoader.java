package org.javaboy.tcrelax.exchange.manager;

import org.javaboy.tcrelax.common.utils.DateUtils;
import org.javaboy.tcrelax.exchange.ExchangeRequest;
import org.javaboy.tcrelax.exchange.config.*;
import org.javaboy.tcrelax.exchange.inner.inventory.InventoryManager;
import org.javaboy.tcrelax.exchange.inner.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author:majin.wj
 */
@Component
public class ExchangeConfigMemoryLoader implements ExchangeConfigLoader {

    private Map<String, ExchangeActivityConfig> configRepository = new HashMap<>();

    @Autowired
    private InventoryManager inventoryManager;

    /**
     * 初始化活动配置
     */
    @PostConstruct
    @Override
    public void initExchangeConfig() {
        ExchangeActivityConfig config = new ExchangeActivityConfig();
        config.setScene("cfHoliday");
        config.setActivityName("穿越火线十一黄金周兑换得好礼");
        config.setStartTime(DateUtils.formatDate("2023-10-01 00:00:00"));
        config.setEndTime(DateUtils.formatDate("2023-10-07 23:59:59"));
        // 活动期间只能兑换十次,活动级别的疲劳度
        ExchangeFatigueConfig activityFatigue = new ExchangeFatigueConfig();
        activityFatigue.setFatigueType(FatigueType.ALL.getType());
        activityFatigue.setAmount(10);
        config.setFatigueConfig(activityFatigue);


        List<ExchangeBenefitConfig> benefitConfigList = new ArrayList<>();
        ExchangeBenefitConfig akBenefit = new ExchangeBenefitConfig();
        akBenefit.setBenefitCode("ak47");
        akBenefit.setUseInventory(true);

        // 库存配置,分小时分配库存
        ExchangeInventoryConfig inventoryConfig = new ExchangeInventoryConfig();
        inventoryConfig.setTotalAmount(5);
        inventoryConfig.setHourAmount(1);
        inventoryConfig.setInventoryStrategy(InventoryStrategyEnum.ALLIN.getType());
        akBenefit.setInventoryConfig(inventoryConfig);

        // 商品疲劳度控制，周期内只能兑换1次
        ExchangeFatigueConfig fatigueConfig = new ExchangeFatigueConfig();
        fatigueConfig.setFatigueType(FatigueType.ALL.getType());
        fatigueConfig.setAmount(1);
        akBenefit.setFatigueConfig(fatigueConfig);

        // 兑换消耗用户权益配置，自定义方式
        ExchangeCostConfig costConfig = new ExchangeCostConfig();
        costConfig.setType(ExchangeCostConfig.CostType.CUSTOM.getType());
        costConfig.setAmount(1);
        akBenefit.setCostConfig(costConfig);
        benefitConfigList.add(akBenefit);
        config.setBenefitConfigList(benefitConfigList);

        configRepository.put("cfHoliday", config);

        // 初始化库存
        inventoryManager.initBenefitInventory(config);
    }

    @Override
    public ExchangeActivityConfig getExchangeConfig(String scene) {
        return configRepository.get(scene);
    }

    public static void main(String[] args) {
        Date date = DateUtils.formatDate("2023-10-01 00:00:00");
        System.out.println(date);

        //Sun Jan 01 00:00:00 CST 2023
    }
}
