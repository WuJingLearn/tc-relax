package org.javaboy.tcrelax;

import org.javaboy.tcrelax.leadboard.LeaderBoardService;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.javaboy.tcrelax.leadboard.scheduler.DailyRankSettleTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TcRelaxApplicationTests {

    @Autowired
    private LeaderBoardService leaderBoardService;

    @Autowired
    private DailyRankSettleTask settleTask;

    @Test
    void contextLoads() {
//
//        leaderBoardService.enterLeaderBoardIncrement("weekRank",6L,-20L);
//        leaderBoardService.enterLeaderBoardIncrement("weekRank",7L,100L);
//        leaderBoardService.enterLeaderBoardIncrement("weekRank",8L,200L);
//        leaderBoardService.enterLeaderBoardIncrement("weekRank",9L,300L);
       /* // 查询前100
        List<Member> members = leaderBoardService.queryTopN("weekRank", 100, 6L);
        System.out.println(members);*/


        leaderBoardService.enterLeaderBoardIncrement("dailyRank",11l,20L);
        leaderBoardService.enterLeaderBoardIncrement("dailyRank",12L,100L);
        leaderBoardService.enterLeaderBoardIncrement("dailyRank",13L,200L);
        leaderBoardService.enterLeaderBoardIncrement("dailyRank",14L,300L);

        settleTask.process();

    }

}
