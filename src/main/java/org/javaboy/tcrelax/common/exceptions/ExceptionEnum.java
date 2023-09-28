package org.javaboy.tcrelax.common.exceptions;

/**
 * @author:majin.wj
 */
public enum ExceptionEnum {

    ACTIVITY_NOT_EXISTS("activity not exist", "活动不存在"),

    ACTIVITY_TIME_ILLEGAL("activity time illegal", "活动未开开始或结束"),
    ;

    private String code;
    private String message;

    private ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
