package org.javaboy.tcrelax.exchange.inner.inventory;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.common.utils.DateUtils;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeBenefitConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeInventoryConfig;
import org.javaboy.tcrelax.exchange.config.InventoryStrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author:majin.wj
 */
@Slf4j
@Component
public class SimpleCacheInventoryManager implements InventoryManager {
    /**
     * 总库存key
     */
    public static final String ACTIVITY_BENEFIT_TOTAL_AMOUNT = "benefit_total_inventory_%s_%s";

    /**
     * 小时库存, scene_benefit_yyyymmdd HH
     */
    public static final String ACTIVITY_BENEFIT_HOUR_AMOUNT = "benefit_hour_inventory_%s_%s_%s";

    @Autowired
    private Jedis jedis;


    @Override
    public void initBenefitInventory(ExchangeActivityConfig activityConfig) {
        String scene = activityConfig.getScene();

        List<ExchangeBenefitConfig> benefitList = activityConfig.getBenefitConfigList();
        for (ExchangeBenefitConfig exchangeBenefitConfig : benefitList) {
            // 不使用库存,表示不限量
            if (!exchangeBenefitConfig.isUseInventory()) {
                continue;
            }
            ExchangeInventoryConfig inventoryConfig = exchangeBenefitConfig.getInventoryConfig();
            // 总限量
            Integer totalAmount = inventoryConfig.getTotalAmount();
            // 小时库存
            Integer hourAmount = inventoryConfig.getHourAmount();
            if (InventoryStrategyEnum.isAllIn(inventoryConfig.getInventoryStrategy())) {
                jedis.set(String.format(ACTIVITY_BENEFIT_TOTAL_AMOUNT, scene, exchangeBenefitConfig.getBenefitCode()), String.valueOf(totalAmount));
                log.info("初始化总库存,scene:{},benefitCode:{},amount:{}", scene, exchangeBenefitConfig.getBenefitCode(), totalAmount);
            } else if (InventoryStrategyEnum.isHour(inventoryConfig.getInventoryStrategy())) {
                Date startTime = activityConfig.getStartTime();
                Calendar instance = Calendar.getInstance();
                instance.setTime(startTime);
                // 波次
                int hiveCount = totalAmount % hourAmount == 0 ? totalAmount / hourAmount : totalAmount / hourAmount + 1;

                for (int i = 0; i < hiveCount; i++) {
                    String hourHiveKey = getHourHiveKey(scene, exchangeBenefitConfig.getBenefitCode(), DateUtils.hourStr(instance.getTime()));
                    Integer curHourAmount = hourAmount;
                    if (i == hiveCount - 1) {
                        curHourAmount = totalAmount % hourAmount == 0 ? hourAmount : totalAmount % hourAmount;
                    }
                    jedis.set(hourHiveKey, String.valueOf(curHourAmount));
                    // 增加1小时
                    instance.add(Calendar.HOUR, 1);
                    log.info("初始化波次库存,scene:{},benefitCode:{},hourHiveKey:{},amount:{}", scene, exchangeBenefitConfig.getBenefitCode(), hourHiveKey, curHourAmount);
                }
            }

        }

    }

    @Override
    public Integer queryBenefitInventory(String scene, String benefitCode, String type) {
        if (InventoryStrategyEnum.isAllIn(type)) {
            String amount = jedis.get(String.format(ACTIVITY_BENEFIT_TOTAL_AMOUNT, scene, benefitCode));
            return Optional.ofNullable(amount).map(Integer::valueOf).orElse(0);
        } else if (InventoryStrategyEnum.isHour(type)) {
            String hourHiveKey = getHourHiveKey(scene, benefitCode, DateUtils.hourStr());
            String amount = jedis.get(hourHiveKey);
            return Optional.ofNullable(amount).map(Integer::valueOf).orElse(0);
        }
        return 0;
    }

    @Override
    public boolean deductBenefitInventory(String scene, String benefitCode, String type, Integer amount) {
        if (InventoryStrategyEnum.isAllIn(type)) {
            String key = String.format(ACTIVITY_BENEFIT_TOTAL_AMOUNT, scene, benefitCode);
            Long remainAmount = jedis.decrBy(key, amount);
            log.info("扣减库存 scene:{},benefitCode:{} 当前还剩库存:{}", scene, benefitCode, remainAmount);
            if (remainAmount < 0) {
                jedis.incrBy(key, amount);
                return false;
            }
            return true;
        } else if (InventoryStrategyEnum.isHour(type)) {
            String hourHiveKey = getHourHiveKey(scene, benefitCode, DateUtils.hourStr());
            Long remainAmount = jedis.decrBy(hourHiveKey, amount);
            log.info("扣减库存scene:{},benefitCode:{} 当前小时还剩库存:{}", scene, benefitCode, remainAmount);
            if (remainAmount < 0) {
                jedis.incrBy(hourHiveKey, amount);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void increaseBenefitInventory(String scene, String benefitCode, String type, Integer amount) {
        if (InventoryStrategyEnum.isAllIn(type)) {
            String key = String.format(ACTIVITY_BENEFIT_TOTAL_AMOUNT, scene, benefitCode);
            Long remainAmount = jedis.incrBy(key, amount);
            log.info("恢复库存 scene:{},benefitCode:{} 当前还剩库存:{}", scene, benefitCode, remainAmount);
        } else if (InventoryStrategyEnum.isHour(type)) {
            String hourHiveKey = getHourHiveKey(scene, benefitCode, DateUtils.hourStr());
            Long remainAmount = jedis.incrBy(hourHiveKey, amount);
            log.info("恢复库存scene:{},benefitCode:{} 当前小时还剩库存:{}", scene, benefitCode, remainAmount);
        }
    }

    private String getHourHiveKey(String scene, String benefitCode, String hourStr) {
        return String.format(ACTIVITY_BENEFIT_HOUR_AMOUNT, scene, benefitCode, hourStr);
    }
}
