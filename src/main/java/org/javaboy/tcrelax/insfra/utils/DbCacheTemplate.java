package org.javaboy.tcrelax.insfra.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.checkerframework.checker.units.qual.A;
import org.javaboy.tcrelax.insfra.redis.RedisClient;
import org.javaboy.tcrelax.insfra.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * @author:majin.wj
 */
@Component
public class DbCacheTemplate {


    @Autowired
    private RedisClient redisClient;

    @Autowired
    private RedisLock redisLock;


    private static final String DB_CACHE_LOCK_PREFIX = "DbCacheLock_";

    public <T> T read(ReadOperation<T> option) {
        CacheKey cacheKey = option.cacheKey();
        String key = cacheKey.getKey();
        // 1.先从缓存读取
        Object data = redisClient.get(key);
        T result = decodeData(data, option.typeReference());
        if (result != null) {
            return result;
        }
        // todo 读数据库，针对同一个key，应该保证单线程访问。
        Lock lock = redisLock.getLock(DB_CACHE_LOCK_PREFIX + key);
        try {
            if(lock.tryLock()) {
                T dbRecord = option.readDb();
                if (dbRecord != null) {
                    String value = encodeData(dbRecord);
                    // 写缓存
                    redisClient.put(key, value, cacheKey.getExpireSeconds());
                }
                return dbRecord;
            }else {
                // 没有获取锁，直接失败；
                throw new RuntimeException("get lock fail");
            }
        }finally {
            lock.unlock();
        }

    }

    public <T> T write(WriteOperation<T> operation) {
        T result = operation.writeDb();
        // 删除缓存
        redisClient.delete(operation.cacheKey().getKey());
        return result;
    }

    private  <T> T decodeData(Object data, TypeReference<T> typeReference) {
        return JSON.parseObject(data == null ? null : data.toString(), typeReference);
    }

    private  <T> String encodeData(T data) {
        return JSON.toJSONString(data);
    }

    public interface ReadOperation<T> {
        T readDb();

        CacheKey cacheKey();

        TypeReference<T> typeReference();

    }

    public interface WriteOperation<T> {
        T writeDb();

        CacheKey cacheKey();

        TypeReference<T> typeReference();

    }


}
