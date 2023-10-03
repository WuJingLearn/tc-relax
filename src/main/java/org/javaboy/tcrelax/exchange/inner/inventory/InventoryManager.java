package org.javaboy.tcrelax.exchange.inner.inventory;

import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;

/**
 * @author:majin.wj
 * 库存中心，库存管理;
 */
public interface InventoryManager {


    /**
     * 在发布活动时，初始化库存
     * 初始化库存
     * @param activityConfig
     */
    public void initBenefitInventory(ExchangeActivityConfig activityConfig);

    /**
     * 查询权益当前库存；
     * @param scene
     * @param benefitCode
     * @param type 库存类型
     * @return
     */
    Integer queryBenefitInventory(String scene,String benefitCode,String type);


    /**
     * 扣减库存
     * @param scene
     * @param benefitCode
     * @param type
     * @param amount
     * @return
     */
    boolean deductBenefitInventory(String scene,String benefitCode,String type,Integer amount);

    void increaseBenefitInventory(String scene,String benefitCode,String type,Integer amount);

}
