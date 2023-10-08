package org.javaboy.relax.draw.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:majin.wj
 *
 * dto和do对象和基本上和数据表中的字段对应；
 * entity实体对象中，可以表达和其他对象的关系，比如说活动包含了权益组的配置
 *
 */
@Data
public class DrawActivityConfigEntity {

    private String activityId;
    private String activityName;

    private Date startTime;
    private Date endTime;

    /**
     * 抽奖模式
     * 概率模式： 根据抽奖组的概率
     * 优先级模式: 根据抽奖组的优先级
     *
     */
    private String drawMode;

    /**
     * 抽奖消耗的用户权益
     */
    private CostBenefitEntity costBenefit;
    /**
     * 抽奖组配置
     */
    private List<DrawGroupConfigEntity> drawGroupConfigs;

}
