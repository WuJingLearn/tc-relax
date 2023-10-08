package org.javaboy.relax.insfra.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj
 */
@Component
public class RedisLock {

    @Autowired
    RedissonClient redissonClient;
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }


}
