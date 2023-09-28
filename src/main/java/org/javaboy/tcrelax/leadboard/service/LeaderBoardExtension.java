package org.javaboy.tcrelax.leadboard.service;

import org.javaboy.tcrelax.insfra.redis.RedisClient;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author:majin.wj 排行榜扩展点
 */
@Component
public class LeaderBoardExtension {

    private Map<Integer, String> levelConfig = new HashMap<>();

    /**
     * 日赛
     */
    private String dailyRankKey = "dailyRank_%s_%s";
    /**
     * 周赛
     */
    private String weekRankKey = "weekRank_%s_%uid";

    @Autowired
    private RedisClient redisClient;


    @PostConstruct
    public void init() {

        levelConfig.put(1000, "贫农");
        levelConfig.put(10000, "地主");
        levelConfig.put(50000, "富豪");
        levelConfig.put(100000, "皇帝");

    }

    @Autowired
    private LeaderBoardConfigLoader configLoader;

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
            return score;
        } else if ("weekRank".equals(bizType)) {
            return score;
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
        String myBidKey = String.format(dailyRankKey, getDateStr(), uid);
        String bid = redisClient.get(myBidKey);
        if (bid != null) {
            return bid;
        }
        String level = levelConfig.get(score);
        bid = "dailyRank_" + level + "_" + getDateStr() + "_" + uid;
        // 日榜保存7天
        redisClient.put(myBidKey, bid, 60 * 60 * 24 * 7);
        return bid;
    }

    private String getWeakRankBid(Long uid, Integer score) {
        String firstDay = getFistDayOfWeak();
        String myBidKey = String.format(weekRankKey, firstDay, uid);
        String bid = redisClient.get(myBidKey);
        if (bid != null) {
            return bid;
        }
        String level = levelConfig.get(score);
        bid = "weekRank_" + level + "_" + firstDay + "_" + uid;
        // 周榜保存10天
        redisClient.put(myBidKey, bid, 60 * 60 * 24 * 10);
        return bid;
    }

    private String getDateStr() {
        Calendar now = Calendar.getInstance();
        return "" + now.get(Calendar.YEAR) + now.get(Calendar.MONTH) + now.get(Calendar.DATE);
    }

    private String getFistDayOfWeak() {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();

        // 设置星期的第一天为周日
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        //礼拜1
        calendar.add(Calendar.DATE, 1);
        return "" + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE);
    }

    private String getBizType(String bid) {
        return bid.split("_")[0];
    }

}
