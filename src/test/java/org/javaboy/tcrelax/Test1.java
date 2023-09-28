package org.javaboy.tcrelax;

import redis.clients.jedis.Jedis;

/**
 * @author:majin.wj
 */
public class Test1 {

    public static void main(String[] args) {

        String luaScript = "local size = redis.call('ZCARD',KEYS[1])" +
                "if(size < tonumber(ARGV[3])) then " +
                "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])" +
                "else" +
                "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])" +
                "redis.call('ZPOPMIN',KEYS[1])" +
                "end";

//        String luaScript =
//                "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])\n" +
//                        "local zSize = redis.call('ZCARD', KEYS[1])\n" +
//                        "if(zSize>tonumber(ARGV[3]))then\n" +
//                        "redis.call('ZPOPMIN',KEYS[1])\n" +
//                        "end\n";


        Jedis jedis = new Jedis();
        Object eval1 = jedis.eval(luaScript, 1, "leader_test4", "10", "zs2", "5");
        jedis.eval(luaScript, 1, "leader_test4", "10", "zs", "5");
        jedis.eval(luaScript, 1, "leader_test4", "11", "ls", "5");
        jedis.eval(luaScript, 1, "leader_test4", "12", "ww", "5");
        jedis.eval(luaScript, 1, "leader_test4", "13", "z6", "5");
        jedis.eval(luaScript, 1, "leader_test4", "100", "z7", "5");
        jedis.eval(luaScript, 1, "leader_test4", "100", "z8", "5");
        System.out.println(eval1);
        jedis.expire("leader_test2",10000);

        System.out.println(jedis.get("name"));

    }
}
