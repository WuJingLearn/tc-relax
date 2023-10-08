package org.javaboy.relax.exchange.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:majin.wj
 */
@Data
public class ActivityDetailDTO {

    private String scene;
    private String activityName;
    private Date startTime;
    private Date endTime;

    /**
     * 所有的权益
     */
    private List<BenefitDTO> benefits;

}
