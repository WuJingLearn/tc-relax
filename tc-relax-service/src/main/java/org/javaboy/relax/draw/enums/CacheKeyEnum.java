package org.javaboy.relax.draw.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author:majin.wj
 */
@AllArgsConstructor
public enum CacheKeyEnum {

    LOTTERY_DRAW(2),

    DRAW_RECORD(60*60*24*30);


    @Getter
    private final int timeout;


    public String getCacheKey(Object... args) {
        if (args == null) {
            return name();
        }
        StringBuffer buffer= new StringBuffer(name());
        for (Object arg : args) {
            buffer.append("_").append(arg);
        }
        return buffer.toString();
    }
}
