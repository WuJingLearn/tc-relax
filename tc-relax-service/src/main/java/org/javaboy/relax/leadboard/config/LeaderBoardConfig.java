package org.javaboy.relax.leadboard.config;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj 排行榜基础信息配置
 */
@Data
public class LeaderBoardConfig {

    /**
     * 业务类型
     */
    private String type;

    /**
     * 排行榜周期,日榜,周榜,自定义
     */
    private String period;

    /**
     * 自定义周期开始时间
     */
    private Date start;

    /**
     * 自定义周期结束时间
     */
    private Date end;

    /**
     * 分桶配置,用于解决写热点问题
     */
    private Integer bucket;

    /**
     * 排行榜上榜数量限制
     */
    private Integer limit;

}
