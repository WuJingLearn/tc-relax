package org.javaboy.relax.draw.dto;

import lombok.Data;

/**
 * @author:majin.wj
 * 抽奖组,一个抽象活动可关联多个抽奖组
 */
@Data
public class DrawGroupDTO {

    /**
     * 关联的活动id
     */
    private String activityId;
    /**
     * 抽奖组id
     */
    private String benefitGroupId;

    /**
     * 抽奖组名称
     */
    private String benefitGroupName;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 概率
     */
    private Long ratio;


}
