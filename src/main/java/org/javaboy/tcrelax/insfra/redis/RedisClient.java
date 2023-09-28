package org.javaboy.tcrelax.insfra.redis;

import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:majin.wj
 */
@Component
public class RedisClient {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private Jedis jedis;


    public String get(String key) {
        return jedis.get(key);
    }

    public boolean put(String key, String value, Integer expireSeconds) {
        jedis.set(key, value);
        jedis.expire(key, expireSeconds);
        return true;
    }

    public boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }

    public void eval(String script, List<Object> keys, Object... values) {
        RScript rscript = redissonClient.getScript();
        rscript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.BOOLEAN, keys, values);
    }
}
