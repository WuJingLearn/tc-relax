package org.javaboy.tcrelax.insfra.redis;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * @author:majin.wj
 */
@Component
public class RedisLock {

    @Autowired
    RedissonClient redissonClient;
    public Lock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }
}
