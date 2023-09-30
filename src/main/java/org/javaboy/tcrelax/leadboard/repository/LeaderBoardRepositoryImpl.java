package org.javaboy.tcrelax.leadboard.repository;

import org.apache.commons.collections4.CollectionUtils;
import org.javaboy.tcrelax.leadboard.config.LeaderBoardConfig;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigRepository;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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


    /**
     * 使用lua脚本保证进入排行时的原子性；
     */
    private String enterLeaderBoardScript = "local size = redis.call('ZCARD', KEYS[1])\n" +
            "if size < tonumber(ARGV[3]) then\n" +
            "    redis.call('ZADD', KEYS[1], ARGV[1], ARGV[2])\n" +
            "else\n" +
            "    redis.call('ZADD', KEYS[1], ARGV[1], ARGV[2])\n" +
            "    redis.call('ZPOPMIN', KEYS[1], 0, 0)\n" +
            "end";


    @Autowired
    private Jedis jedis;

    @Autowired
    private LeaderBoardConfigRepository configRepository;

    @Override
    public void enterLeaderBoard(String bid, Long uid, Long score) {
        LeaderBoardConfig leaderBoardConfig = configRepository.getLeaderBoardConfig(getBizType(bid));
        if (leaderBoardConfig == null) {
            return;
        }
        // 分桶数量
        Integer buckets = leaderBoardConfig.getBucket();
        // 排行榜数量限制
        Integer limit = leaderBoardConfig.getLimit();
        int hitBucket = getUserBucket(buckets, uid);
        String bucketKey = String.format(LEADER_KEY, bid, hitBucket);
        jedis.eval(enterLeaderBoardScript, 1, bucketKey, String.valueOf(score), String.valueOf(uid), String.valueOf(limit));
    }


    @Override
    public Long queryScore(String bid, Long uid) {
        LeaderBoardConfig leaderBoardConfig = configRepository.getLeaderBoardConfig(getBizType(bid));
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
        LeaderBoardConfig leaderBoardConfig = configRepository.getLeaderBoardConfig(getBizType(bid));
        if (leaderBoardConfig == null) {
            return Collections.emptyList();
        }
        int limit = Math.min(topN, leaderBoardConfig.getLimit());
        Queue<Tuple> queue = new PriorityQueue<>(limit, Comparator.reverseOrder());
        for (int i = 0; i < leaderBoardConfig.getBucket(); i++) {
            String bucketKey = String.format(LEADER_KEY, bid, i);
            Set<Tuple> tuples = jedis.zrevrangeWithScores(bucketKey, 0, limit);
            if (CollectionUtils.isNotEmpty(tuples)) {
                queue.addAll(tuples);
            }
        }
        return IntStream.range(0, limit).mapToObj(rank -> {
                    Tuple tuple = queue.poll();
                    if (tuple == null) {
                        return null;
                    }
                    Member member = new Member();
                    member.setId(tuple.getElement());
                    member.setScore(Double.valueOf(tuple.getScore()).longValue());
                    member.setRank(rank + 1);
                    return member;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    private int getUserBucket(int buckets, Long uid) {
        return (int) (uid % buckets);
    }


    private String getBizType(String bid) {
        return bid.split("_")[0];
    }
}
