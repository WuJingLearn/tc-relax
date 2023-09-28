package org.javaboy.tcrelax.draw.dataobject;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj
 * 权益活动
 */
@Data
public class BenefitActivityDO {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String activityId;

    private String activityName;

    private Date startTime;

    private Date endTime;

    /**
     * 配置信息
     */
    private String configInfo;

    /**
     * 类型
     * 0: 兑换活动
     * 1: 抽奖活动
     */
    private int type;

}
