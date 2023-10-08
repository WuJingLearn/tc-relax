package org.javaboy.relax.draw.enums;

/**
 * @author:majin.wj
 */
public enum LotteryOptionEnum {

    DRAW,PREVIEW;


    public boolean isSameOption(String option) {
        return this.name().equals(option);
    }
}
