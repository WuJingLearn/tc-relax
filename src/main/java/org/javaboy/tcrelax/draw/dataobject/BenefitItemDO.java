package org.javaboy.tcrelax.draw.dataobject;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj
 * 单个权益项
 */
@Data
public class BenefitItemDO {

    /**
     * 关联的活动id
     */
    private String activityId;
    /**
     * 关联的权益组id
     */
    private String benefitGroupId;
    /**
     * 权益id
     */
    private String benefitId;

    private String benefitName;

    /**
     * 配置信息
     * 库存信息
     * 无聊信息等
     */
    private String configInfo;

    private Date gmtCreate;

    private Date gmtModified;



}
