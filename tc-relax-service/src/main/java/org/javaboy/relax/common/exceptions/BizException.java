package org.javaboy.relax.common.exceptions;

/**
 * @author:majin.wj 业务异常; 表示程序执行中,一些可以预知的会触发的异常
 */
public class BizException extends RuntimeException {


    private ExceptionEnum exceptionEnum;

    private String code;
    private String msg;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.exceptionEnum = exceptionEnum;
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        if (exceptionEnum != null) {
            return exceptionEnum.getCode();
        }
        return code;
    }

    @Override
    public String getMessage() {
        if (exceptionEnum != null) {
            return exceptionEnum.getMessage();
        }
        return msg;
    }
}
