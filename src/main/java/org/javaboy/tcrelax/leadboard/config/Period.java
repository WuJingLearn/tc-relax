package org.javaboy.tcrelax.leadboard.config;

/**
 * @author:majin.wj
 */
public enum Period {
    DAY("day"),WEAK("weak"),CUSTOM("custom");

    private String period;
    private Period(String period){
        this.period = period;
    }

    public static boolean isCustom(String type) {
        return CUSTOM.period.equals(type);
    }
}
