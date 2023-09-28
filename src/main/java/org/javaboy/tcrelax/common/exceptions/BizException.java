package org.javaboy.tcrelax.common.exceptions;

/**
 * @author:majin.wj 业务异常; 表示程序执行中,一些可以预知的会触发的异常
 */
public class BizException extends RuntimeException {


    private ExceptionEnum exceptionEnum;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    @Override
    public String getMessage() {
        return exceptionEnum.getMessage();
    }
}
