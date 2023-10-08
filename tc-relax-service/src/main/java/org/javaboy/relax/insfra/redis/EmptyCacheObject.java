package org.javaboy.relax.insfra.redis;

/**
 * @author:majin.wj
 */
public class EmptyCacheObject implements CacheObject{


    @Override
    public Object getObject() {
        return null;
    }

}
