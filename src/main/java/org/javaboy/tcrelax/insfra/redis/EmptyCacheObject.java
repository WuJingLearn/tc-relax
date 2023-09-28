package org.javaboy.tcrelax.insfra.redis;

/**
 * @author:majin.wj
 */
public class EmptyCacheObject implements CacheObject{


    @Override
    public Object getObject() {
        return null;
    }

}
