package org.javaboy.tcrelax.exchange.config;

/**
 * @author:majin.wj
 */
public enum InventoryStrategyEnum {
    /**
     * 总库存一次性透出
     */
    ALLIN("allIn"),
    /**
     * 总库存
     */
    HOUR("hour"),

    ;

    private String type;

    private InventoryStrategyEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static boolean isAllIn(String type) {
        return ALLIN.getType().equals(type);
    }

    public static boolean isHour(String type) {
        return HOUR.getType().equals(type);
    }
}
