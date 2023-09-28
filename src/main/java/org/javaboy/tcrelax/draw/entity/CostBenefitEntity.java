package org.javaboy.tcrelax.draw.entity;

import lombok.Data;

/**
 * @author:majin.wj
 * 抽奖消耗的权益
 */
@Data
public class CostBenefitEntity {

    private String type;

    private String awardType;

    private String awardName;

    private Integer amount;

}
