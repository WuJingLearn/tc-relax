package org.javaboy.relax.draw.dto;

import lombok.Data;
import org.javaboy.relax.draw.entity.CostBenefitEntity;

/**
 * @author:majin.wj
 * 抽奖活动
 */
@Data
public class DrawActivityDTO {

    private String activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动开始时间
     */
    private Long startTime;
    /**
     * 活动结束时间
     */
    private Long endTime;
    /**
     * 抽奖模式
     */
    private String drawModel;
    /**
     * 库存定时任务id
     */
    private Long schedulerId;
    /**
     * 抽象需要消耗的用户权益
     */
    private CostBenefitEntity costBenefit;
    /**
     * 活动状态
     */
    private int status;
}
