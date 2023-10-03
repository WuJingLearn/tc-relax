package org.javaboy.tcrelax.common.exceptions;

/**
 * @author:majin.wj
 */
public enum ExceptionEnum {

    ACTIVITY_NOT_EXISTS("activity not exist", "活动不存在"),

    ACTIVITY_TIME_ILLEGAL("activity time illegal", "不在活动时间"),

    BENEFIT_NOT_EXISTS("benefit not exist","权益不存在"),

    ACTIVITY_FATIGUE("activity arrive fatigue", "活动触发疲劳度限制"),

    BENEFIT_FATIGUE("benefit arrive fatigue", "权益触发疲劳度限制"),

    EXCHANGE_NO_ENOUGH_COST("exchange conditional not arrived","兑换条件不足"),

    BENEFIT_INVENTORY_NOT_ENOUGH("benefit inventory not enough","库存不足"),

    GET_LOCK_FAIL("get lock fail","获取分布式锁失败"),

    SYSTEM_ERROR("system error","系统异常")

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
