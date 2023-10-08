package org.javaboy.relax.draw.enums;


public enum LotteryResultEnum {
    SUCCESS("SUCCESS", "成功"),
    LOCK_FAIL("LOCK_FAIL", "未获取到并发锁"),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    ILLEGAL_ARGUMENTS("ILLEGAL_ARGUMENTS", "参数异常"),
    SAFE_ILLEGAL_ARGUMENTS("SAFE_ILLEGAL_ARGUMENTS", "缺少安全校验参数"),
    ACTIVITY_NOT_CONFIG("ACTIVITY_NOT_CONFIG", "活动配置未配置"),
    BENEFIT_NOT_CONFIG("BENEFIT_NOT_CONFIG", "权益未配置"),
    ACTIVITY_TIME_INVALID("ACTIVITY_TIME_INVALID", "非活动时间内"),
    DRAW_CHANNEL_NOT_SUPPORT("DRAW_CHANNEL_NOT_SUPPORT", "抽奖渠道不支持"),
    OUTER_ID_CONFLICT("OUTER_ID_CONFLICT", "幂等冲突"),
    TPP_DRAW_ERROR("TPP_DRAW_ERR", "算法抽奖发生未知错误"),
    FATIGUE_CHECK("FATIGUE_CHECK", "疲劳度达到上限"),
    INVENTORY_NOT_ENOUGH("INVENTORY_NOT_ENOUGH", "库存不足"),
    ASSET_NOT_ENOUGH("PROPERTY_NOT_ENOUGH", "用户资产不足"),
    AWARD_FAIL("AWARD_FAIL", "奖励发放失败"),
    CROWD_NOT_CONFIG("CROWD_NOT_CONFIG", "人群配置不存在"),
    NOT_HIT_CROWD("CROWD_NOT_CONFIG", "未命中人群"),
    NOT_SUPPORT_PROPERTY("NOT_SUPPORT_PROPERTY", "不支持此类资产扣减"),
    QUERY_PROPERTY_FAIL("QUERY_PROPERTY_FAIL", "查询用户资产失败"),
    RESULT_EMPTY("RESULT_EMPTY", "抽奖结果为空");

    private final String code;
    private final String desc;

    private LotteryResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
