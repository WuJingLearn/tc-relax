package org.javaboy.tcrelax.exchange.inner.inventory;

import org.javaboy.tcrelax.exchange.ExchangeContext;

/**
 * @author:majin.wj
 */
public interface InventoryService {


    Integer queryBenefitInventory(String scene,String benefitCode,String inventoryStrategy);


    boolean deductBenefitInventory(ExchangeContext context);

    void increaseBenefitInventory(ExchangeContext context);
}
