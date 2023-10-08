package org.javaboy.relax.exchange;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class ExchangeRequest {
    private String uid;
    private String scene;

    /**
     * 请求兑换的权益code
     */
    private String benefitCode;

    private String outId;

}
