package org.javaboy.tcrelax.insfra.redis.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author:majin.wj
 */
@Configuration
public class RedisConfiguration {

    @Value("${redis.adder}")
    private String address;
    @Bean
    public Jedis jedis() {
        return new Jedis(address);
    }

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress(address);
        return Redisson.create(config);
    }
}

