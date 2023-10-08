package org.javaboy.relax.draw.core.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author:majin.wj
 * 奖励基础信息,用于抽奖，兑换展示
 */
@Data
public class AwardInfo {

    private String awardType;
    private String awardName;
    private Long awardAmount;
    private String imgUrl;
    private Map<String,Object> extraDatas;
}
