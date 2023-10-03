package org.javaboy.tcrelax.exchange.dto;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class BenefitDTO {
    /**
     * 权益code
     */
    private String benefitCode;
    /**
     * 图片url
     */
    private String url;

    /**
     * 是否有总额限制.不限量显示不限量文案，限量显示totalAmount数额
     */
    private boolean totalLimit;
    /**
     * 总额数量
     */
    private Integer totalAmount;

    /**
     * 当前小时是否还有剩余： 仅仅需要显示是还是否
     */
    private boolean hourAmount;
}
