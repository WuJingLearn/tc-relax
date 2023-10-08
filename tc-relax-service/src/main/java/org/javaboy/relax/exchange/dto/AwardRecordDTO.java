package org.javaboy.relax.exchange.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author:majin.wj
 */
@Data
public class AwardRecordDTO {

    private String uid;
    private String scene;
    private String benefitCode;
    private String url;
    private String outId;
    private Date createTime;

}
