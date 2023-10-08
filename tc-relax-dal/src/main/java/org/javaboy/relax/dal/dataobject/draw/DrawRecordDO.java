package org.javaboy.relax.dal.dataobject.draw;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj
 */
@Data
public class DrawRecordDO {

    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    private Long userId;
    private String outId;
    private String activityId;

    private String benefitGroupId;
    private String benefitId;

    private String awardType;
    private String awardName;
    private Long awardAmount;

    private Integer status;
}
