package org.javaboy.tcrelax.leadboard.scheduler;

import com.google.common.collect.Lists;
import org.javaboy.tcrelax.leadboard.LeaderBoardService;
import org.javaboy.tcrelax.leadboard.extension.LeaderBoardExtension;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigRepository;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj 日赛排行榜结算, 奖励发放；
 */
@Component
public class DailyRankSettleTask {

    @Autowired
    LeaderBoardConfigRepository leaderBoardConfigRepository;

    @Autowired
    LeaderBoardExtension extension;

    @Autowired
    LeaderBoardService leaderBoardService;

    public void process() {
        Map<Integer, String> levelConfig = extension.getLevelConfig();
        List<String> levels = Lists.newArrayList(levelConfig.values());
        for (String level : levels) {
            String leaderBoardKey = extension.getDailyRankLogicLeaderBoardKey(level);
            List<Member> member = leaderBoardService.queryTopN(leaderBoardKey, 10);

            System.out.println("当前段位:" + level + " 成员有:" + member);
        }
    }

}
