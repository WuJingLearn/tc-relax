package org.javaboy.tcrelax.exchange.config;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class ExchangeCostConfig {

    private String type;
    private Integer amount;

    public enum CostType {
        /**
         * 业务自定义
         */
        CUSTOM("custom"),
        /**
         * 统一权益消耗
         */
        UNIFIED("unified");

        private String type;

        private CostType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static boolean isCustom(String type) {
            return CUSTOM.type.equals(type);
        }

        public static boolean isUnified(String type) {
            return UNIFIED.type.equals(type);
        }
    }

}
