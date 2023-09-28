package org.javaboy.tcrelax.draw.core.request;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class DrawRequest {

    private Long userId;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 幂等Id
     */
    private String outId;

    /**
     * 抽奖次数
     */
    private Integer drawTimes;
}
