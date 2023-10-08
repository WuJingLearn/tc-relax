package org.javaboy.relax.exchange.inner.inventory;

import org.javaboy.relax.common.exceptions.BizException;
import org.javaboy.relax.common.exceptions.ExceptionEnum;
import org.javaboy.relax.exchange.ExchangeContext;
import org.javaboy.relax.exchange.config.ExchangeBenefitConfig;
import org.javaboy.relax.exchange.config.ExchangeInventoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj 支持多种库存管理方式，先使用最基本的单个key的方式；
 */
@Component
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryManager inventoryManager;

    @Override
    public Integer queryBenefitInventory(String scene, String benefitCode, String inventoryStrategy) {
        return inventoryManager.queryBenefitInventory(scene, benefitCode, inventoryStrategy);
    }

    @Override
    public boolean deductBenefitInventory(ExchangeContext context) {
        ExchangeBenefitConfig benefitConfig = context.getBenefitConfig();
        ExchangeInventoryConfig inventoryConfig = benefitConfig.getInventoryConfig();
        String inventoryStrategy = inventoryConfig.getInventoryStrategy();
        boolean deductResult = inventoryManager.deductBenefitInventory(context.getScene(), benefitConfig.getBenefitCode(), inventoryStrategy, 1);
        if (!deductResult) {
            // 库存不足。
            throw new BizException(ExceptionEnum.BENEFIT_INVENTORY_NOT_ENOUGH);
        }
        context.setDeductInventoryResult(true);
        return true;
    }

    @Override
    public void increaseBenefitInventory(ExchangeContext context) {
        ExchangeBenefitConfig benefitConfig = context.getBenefitConfig();
        ExchangeInventoryConfig inventoryConfig = benefitConfig.getInventoryConfig();
        String inventoryStrategy = inventoryConfig.getInventoryStrategy();
        inventoryManager.increaseBenefitInventory(context.getScene(), benefitConfig.getBenefitCode(), inventoryStrategy, 1);
    }
}
