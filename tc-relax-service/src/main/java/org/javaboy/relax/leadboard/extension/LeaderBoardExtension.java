package org.javaboy.relax.leadboard.extension;

import org.apache.commons.lang3.StringUtils;
import org.javaboy.relax.insfra.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author:majin.wj 排行榜扩展点
 * 这里可以重构一下。
 */
@Component
public class LeaderBoardExtension {

    private Map<Integer, String> levelConfig = new TreeMap<>();

    /**
     * 日赛 用户所在分区
     */
    private String userDailyRankKey = "userDailyRank_%s_%s";

    /**
     * s1:等级
     * s2:日期
     */
    private String dailyRankKey = "dailyRank_%s_%s";

    /**
     * 记录用户当天获取总经验
     */
    private String dailyRankUserTotalScoreKey = "dailyRankScore_%s_%s";

    /**
     * 周赛 用户所在分区
     */
    private String userWeekRankKey = "userWeekRank_%s_%s";

    /**
     * s1:等级
     * s2:日期
     */
    private String weekRankKey = "weekRank_%s_%s";

    /**
     * 记录用户当周获取的总经验
     */
    private String weekRankUserTotalScoreKey = "weakRankScore_%s_%s";

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private Jedis jedis;


    @PostConstruct
    public void init() {
        levelConfig.put(1000, "pinglong");
        levelConfig.put(10000, "dizhu");
        levelConfig.put(50000, "fuhao");
        levelConfig.put(100000, "huangdi");
    }


    /**
     * 获取bid
     *
     * @param bizType
     * @param userId
     * @return
     */
    public String getBid(String bizType, Long userId) {
        if ("energyPk".equals(bizType)) {
            // 不分榜，查看赛季开始时间，发挥
            return "enerygyPk";
        } else if ("dailyRank".equals(bizType)) {
            // 分榜，查看赛事时间，用户所在等级数据拿个
            return getDailyRankBid(userId, ThreadLocalRandom.current().nextInt(100000));
        } else if ("weekRank".equals(bizType)) {
            return getWeakRankBid(userId, ThreadLocalRandom.current().nextInt(100000));
        }
        return null;
    }

    /**
     * 当是按照增量方式进入排行榜时,需要查询历史分数;
     * score可以为负数
     *
     * @param bid
     * @param userId
     * @param score
     * @return
     */
    public Long getTotalScore(String bid, Long userId, Long score) {
        String bizType = getBizType(bid);
        if ("energyPk".equals(bizType)) {
            return score;
        } else if ("dailyRank".equals(bizType)) {
            // todo get/set非原子,所以这里需要并发控制
            String key = String.format(dailyRankUserTotalScoreKey, getDateStr(), userId);
            String totalScore = jedis.get(key);
            if (totalScore == null) {
                jedis.set(key, String.valueOf(score));
            } else {
                jedis.set(key, String.valueOf(Long.parseLong(totalScore) + score));
            }
            return Long.valueOf(jedis.get(key));
        } else if ("weekRank".equals(bizType)) {
            String key = String.format(weekRankUserTotalScoreKey, getFistDayOfWeak(), userId);
            String totalScore = jedis.get(key);
            if (totalScore == null) {
                jedis.set(key, String.valueOf(score));
            } else {
                jedis.set(key, String.valueOf(Long.parseLong(totalScore) + score));
            }
            return Long.valueOf(jedis.get(key));
        }
        return score;
    }


    /**
     * 日赛榜单,根据用户的经验值获取榜单;
     * 榜单id在赛事第一次访问时确认;
     *
     * @param uid
     * @return
     */
    private String getDailyRankBid(Long uid, Integer score) {
        String myBidKey = String.format(userDailyRankKey, getDateStr(), uid);
        String bid = redisClient.get(myBidKey);
        if (bid != null) {
            return bid;
        }
        // 获取用户段位
        String level = null;
        for (Map.Entry<Integer, String> entry : levelConfig.entrySet()) {
            if (score < entry.getKey()) {
                level = entry.getValue();
                break;
            }
        }
        // 逻辑榜单key，用户当前被分到哪个逻辑榜单
        bid = String.format(dailyRankKey, level, getDateStr());
        // 日榜保存7天
        redisClient.put(myBidKey, bid, 60 * 60 * 24 * 7);
        return bid;
    }

    private String getWeakRankBid(Long uid, Integer score) {
        String firstDay = getFistDayOfWeak();
        String myBidKey = String.format(userWeekRankKey, firstDay, uid);
        String bid = redisClient.get(myBidKey);
        if (bid != null) {
            return bid;
        }
        // 获取用户段位
        String level = null;
        for (Map.Entry<Integer, String> entry : levelConfig.entrySet()) {
            if (score < entry.getKey()) {
                level = entry.getValue();
                break;
            }
        }
        bid = String.format(weekRankKey, level, getDateStr());
        // 周榜保存10天
        redisClient.put(myBidKey, bid, 60 * 60 * 24 * 10);
        return bid;
    }

    private String getDateStr() {
        Calendar calendar = Calendar.getInstance();
        return  StringUtils.join(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH) + 1),calendar.get(Calendar.DATE),"");
    }

    private String getFistDayOfWeak() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return StringUtils.join(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH) + 1),calendar.get(Calendar.DATE),"");
    }

    public String getDailyRankLogicLeaderBoardKey(String level){
        return String.format(dailyRankKey, level, getDateStr());
    }


    private String getBizType(String bid) {
        return bid.split("_")[0];
    }

    public Map<Integer,String> getLevelConfig(){
        return Collections.unmodifiableMap(levelConfig);
    }

}
