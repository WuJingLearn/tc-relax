package org.javaboy.tcrelax.leadboard.scheduler;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javaboy.tcrelax.leadboard.LeaderBoardService;
import org.javaboy.tcrelax.leadboard.extension.LeaderBoardExtension;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigRepository;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj 日赛排行榜结算, 奖励发放；
 */
@Slf4j
@Component
public class DailyRankSettleTask {

    @Autowired
    LeaderBoardConfigRepository leaderBoardConfigRepository;

    @Autowired
    LeaderBoardExtension extension;

    @Autowired
    LeaderBoardService leaderBoardService;

    @Autowired
    private Jedis jedis;

    /**
     * 当天结算任务完成标识
     */
    private String finishFlag = "dailyRankSettleTask_%s";


    public void process() {
        if (jedis.get(getFinishKey()) != null) {
            return;
        }
        Map<Integer, String> levelConfig = extension.getLevelConfig();
        List<String> levels = Lists.newArrayList(levelConfig.values());
        for (String level : levels) {
            String leaderBoardKey = extension.getDailyRankLogicLeaderBoardKey(level);
            List<Member> member = leaderBoardService.queryTopN(leaderBoardKey, 10);
            System.out.println("当前段位:" + level + " 成员有:" + member);
        }
        // 设置完成标识
        jedis.set(getFinishKey(),"true");
    }


    private void sendAward(Member member,String level) {

        //1.发放奖励 构建幂等id
        String outId = String.format("dailyRankAward_%s_%s",getDateStr(),member.getId());
        //2.插入数据库
        try {
            // 根据uid, dailiRank 构建唯一索引。当记录
            insertAwardRecord(member,level);
        }catch (DuplicateKeyException duplicateKeyException){
            log.info("日赛,记录奖励记录冲突；幂等");
        }

    }

    private void insertAwardRecord(Member member,String level){

    }

    private String getFinishKey(){
        return String.format(finishFlag,getDateStr());
    }
    private String getDateStr() {
        Calendar calendar = Calendar.getInstance();
        return  StringUtils.join(calendar.get(Calendar.YEAR),(calendar.get(Calendar.MONTH) + 1),calendar.get(Calendar.DATE),"");
    }

}
