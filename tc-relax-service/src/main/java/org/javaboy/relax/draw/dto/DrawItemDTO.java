package org.javaboy.relax.draw.dto;

import lombok.Data;
import org.javaboy.relax.draw.entity.InventoryConfigEntity;

/**
 * @author:majin.wj
 * 具体的抽象项
 */
@Data
public class DrawItemDTO {

    private String activityId;

    private String benefitGroupId;

    private String benefitId;

    private String benefitName;

    /**
     * 抽奖比例
     */
    private Long ratio;

    /**
     * 透出顺序
     */
    private int order;

    /**
     * 奖励类型,奖励类型为具体发放的权益，需要跟具体
     * 的奖励系统进行对接；
     */
    private String awardType;

    private String awardName;


    /**
     * 发放的数量
     */
    private Integer amount;

    /**
     * 是否使用库存
     */
    private boolean useInventory;


    /**
     * 库存
     */
    private InventoryConfigEntity inventory;


}
