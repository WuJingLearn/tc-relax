package org.javaboy.tcrelax.insfra.utils;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class CacheKey {

    private String key;
    private Integer expireSeconds;

    public static CacheKey of(String key,Integer expireSeconds) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.setKey(key);
        cacheKey.setExpireSeconds(expireSeconds);
        return cacheKey;
    }

}
