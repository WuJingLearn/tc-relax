package org.javaboy.tcrelax;

import redis.clients.jedis.Jedis;

import java.util.Calendar;

/**
 * @author:majin.wj
 */
public class Test1 {

    public static void main(String[] args) {
//
//        String luaScript = "local size = redis.call('ZCARD',KEYS[1])" +
//                "if(size < tonumber(ARGV[3])) then " +
//                "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])" +
//                "else" +
//                "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])" +
//                "redis.call('ZPOPMIN',KEYS[1])" +
//                "end";
//
//
//
//        Jedis jedis = new Jedis();
//
//        jedis.eval(luaScript,1,"bucketKey4",String.valueOf(100),String.valueOf(123),String.valueOf(01));


        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //
        String str = "" + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.DATE);
        System.out.println(str);

    }
}
