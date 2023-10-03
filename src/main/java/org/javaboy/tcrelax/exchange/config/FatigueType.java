package org.javaboy.tcrelax.exchange.config;

public enum FatigueType {
    // 天内
    DAY("day"), // 周内
    WEEK("week"), // 周期内
    ALL("all");


    private String type;

    FatigueType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}