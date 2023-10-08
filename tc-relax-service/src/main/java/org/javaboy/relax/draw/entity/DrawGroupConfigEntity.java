package org.javaboy.relax.draw.entity;

import lombok.Data;

import java.util.List;

/**
 * @author:majin.wj
 */
@Data
public class DrawGroupConfigEntity {

    private String activityId;
    private String benefitGroupId;
    private String benefitGroupName;
    /**
     * 抽奖比例，万分比
     */
    private long ratio;
    /**
     * 优先级
     */
    private int priority;
    /**
     * 单个抽奖项
     */
    private List<DrawItemConfigEntity> drawItems;

}
