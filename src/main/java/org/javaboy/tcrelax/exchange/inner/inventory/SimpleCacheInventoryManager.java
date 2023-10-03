package org.javaboy.tcrelax.exchange.inner.inventory;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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


    /**
     * 初始化库存: 根据库存策略,将库存提前设置到缓存中
     *
     * @param activityConfig
     */
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
            } else if (InventoryStrategyEnum.isHour(inventoryConfig.getInventoryStrategy())) {
                Date startTime = activityConfig.getStartTime();
                Calendar instance = Calendar.getInstance();
                instance.setTime(startTime);
                // 波次
                int hiveCount = totalAmount % hourAmount == 0 ? totalAmount / hourAmount : totalAmount / hourAmount + 1;

                for (int i = 0; i < hiveCount; i++) {
                    instance.add(Calendar.DATE, i);
                    int year = instance.get(Calendar.YEAR);
                    int month = instance.get(Calendar.MONTH) + 1;
                    int day = instance.get(Calendar.DATE);
                    int hour = instance.get(Calendar.HOUR);
                    String hourHiveKey = getHourHiveKey(scene, exchangeBenefitConfig.getBenefitCode(), year, month, day, hour);
                    Integer curHourAmount = hourAmount;
                    if (i == hiveCount - 1) {
                        curHourAmount = totalAmount % hourAmount;
                    }
                    jedis.set(hourHiveKey, String.valueOf(curHourAmount));
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
            Calendar instance = Calendar.getInstance();
            String hourHiveKey = getHourHiveKey(scene, benefitCode, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE), instance.get(Calendar.HOUR));
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
            if (remainAmount < 0) {
                jedis.incrBy(key, amount);
                return false;
            }
            return true;
        } else if (InventoryStrategyEnum.isHour(type)) {
            Calendar instance = Calendar.getInstance();
            String hourHiveKey = getHourHiveKey(scene, benefitCode, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE), instance.get(Calendar.HOUR));
            Long remainAmount = jedis.decrBy(hourHiveKey, amount);
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
            jedis.incrBy(key, amount);
        } else if (InventoryStrategyEnum.isHour(type)) {
            Calendar instance = Calendar.getInstance();
            String hourHiveKey = getHourHiveKey(scene, benefitCode, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DATE), instance.get(Calendar.HOUR));
            jedis.incrBy(hourHiveKey, amount);
        }
    }

    private String getHourHiveKey(String scene, String benefitCode, int year, int month, int day, int hour) {
        return String.format(ACTIVITY_BENEFIT_HOUR_AMOUNT, scene, benefitCode, StringUtils.join(year, month, day, hour));
    }
}
