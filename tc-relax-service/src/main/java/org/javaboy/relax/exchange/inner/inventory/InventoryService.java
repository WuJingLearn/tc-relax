package org.javaboy.relax.exchange.inner.inventory;

import org.javaboy.relax.exchange.ExchangeContext;

/**
 * @author:majin.wj
 */
public interface InventoryService {


    Integer queryBenefitInventory(String scene,String benefitCode,String inventoryStrategy);


    boolean deductBenefitInventory(ExchangeContext context);

    void increaseBenefitInventory(ExchangeContext context);
}
