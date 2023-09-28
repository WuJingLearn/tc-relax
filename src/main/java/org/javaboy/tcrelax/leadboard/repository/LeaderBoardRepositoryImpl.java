package org.javaboy.tcrelax.leadboard.repository;

import com.google.common.collect.Lists;
import org.javaboy.tcrelax.insfra.redis.RedisClient;
import org.javaboy.tcrelax.leadboard.config.LeaderBoardConfig;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigLoader;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author:majin.wj
 */
@Component
public class LeaderBoardRepositoryImpl implements LeaderBoardRepository {

    /**
     * 排榜榜中一个桶的key
     * 1.榜单id
     * 2.桶编号
     */
    private static final String LEADER_KEY = "leaderboard_%s_%s";

    // value的参 score member limit
    private String enterLeaderBoardScript = "local size = redis.call('ZCARD',KEYS[1])\n" + "if(size<tonumner(ARGV[3]) then" + "redis.call('ZADD',KEYS[1],ARGV[1]),ARGV[2])" + "else" + "redis.call('ZADD',KEYS[1],ARGV[1],ARGV[2])" + "redis.call('ZPOPMIN',KEYS[1])" + "end";

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private Jedis jedis;

    @Autowired
    private LeaderBoardConfigLoader configLoader;

    @Override
    public void enterLeaderBoard(String bid, Long uid, Long score) {
        LeaderBoardConfig leaderBoardConfig = configLoader.getLeaderBoard(getBizType(bid));
        if (leaderBoardConfig == null) {
            return;
        }
        // 分桶数量
        Integer buckets = leaderBoardConfig.getBucket();
        // 排行榜数量限制
        Integer limit = leaderBoardConfig.getLimit();
        int hitBucket = getUserBucket(buckets, uid);
        String bucketKey = String.format(LEADER_KEY, bid, hitBucket);
        redisClient.eval(enterLeaderBoardScript, Lists.newArrayList(bucketKey), Lists.newArrayList(score, uid, limit));
    }


    @Override
    public Long queryScore(String bid, Long uid) {
        LeaderBoardConfig leaderBoardConfig = configLoader.getLeaderBoard(getBizType(bid));
        if (leaderBoardConfig == null) {
            return 0l;
        }
        // 分桶数量
        Integer buckets = leaderBoardConfig.getBucket();
        // 排行榜数量限制
        int hitBucket = getUserBucket(buckets, uid);
        String bucketKey = String.format(LEADER_KEY, bid, hitBucket);
        Double score = jedis.zscore(bucketKey, String.valueOf(uid));
        return score == null ? 0L : score.longValue();
    }

    @Override
    public List<Member> queryTopN(String bid, Integer topN) {
        LeaderBoardConfig leaderBoardConfig = configLoader.getLeaderBoard(getBizType(bid));
        if (leaderBoardConfig == null) {
            return Collections.emptyList();
        }
        int limit = Math.min(topN, leaderBoardConfig.getLimit());
        Queue<Member> queue = new PriorityQueue(limit, Comparator.reverseOrder());
        for (int i = 0; i < leaderBoardConfig.getBucket(); i++) {
            String bucketKey = String.format(LEADER_KEY, bid, i);
            Set<Tuple> tuples = jedis.zrevrangeWithScores(bucketKey, 0, limit);
            tuples.stream().map(tuple -> {
                Member member = new Member();
                member.setId(tuple.getElement());
                member.setScore(Double.valueOf(tuple.getScore()).longValue());
                return member;
            }).forEach(queue::add);
        }
        return Stream.generate(queue::poll).limit(limit).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Queue<String> list = new PriorityQueue<>();
        list.add("zs");
        list.add("ls");
        list.add("ww");
        List<String> collect = Stream.generate(list::poll).limit(1).collect(Collectors.toList());
        System.out.println(collect);
    }

    private int getUserBucket(int buckets, Long uid) {
        return (int) (uid % buckets);
    }


    private String getBizType(String bid) {
        return bid.split("_")[0];
    }
}
