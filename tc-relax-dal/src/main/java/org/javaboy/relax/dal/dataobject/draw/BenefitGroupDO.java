package org.javaboy.relax.dal.dataobject.draw;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj
 * 权益组
 */
@Data
public class BenefitGroupDO {

    /**
     * 关联的活动id
     */
    private String activityId;

    private String benefitGroupId;
    private String benefitGroupName;

    /**
     * 配置信息
     */
    private String configInfo;

    private Date gmtCreate;

    private Date gmtModified;

}
