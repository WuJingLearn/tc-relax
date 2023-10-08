package org.javaboy.relax.draw.entity;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class DrawItemConfigEntity {

    private String benefitId;
    private String benefitName;

    private String awardType;
    private String awardName;
    /**
     * 发放的数量
     */
    private long amount;

    /**
     * 抽奖比例
     */
    private long ratio;

    /**
     * 透出顺序
     */
    private int order;

    /**
     * 使用库存
     */
    private boolean useInventory;

    /**
     * 库存配置
     */
    private InventoryConfigEntity inventoryConfig;

}
