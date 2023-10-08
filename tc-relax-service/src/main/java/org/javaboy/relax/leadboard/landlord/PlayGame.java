package org.javaboy.relax.leadboard.landlord;

import org.javaboy.relax.leadboard.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj
 */
@Component
public class PlayGame {

    @Autowired
    LeaderBoardService leaderBoardService;


    public void play(Long userId, Long score) {
        // 用户等级
        String level = "level1";
        // 用户榜单 这个榜单在一次活动中应该被提前创建
        String bid = "LandlordDaily:" + level;
        leaderBoardService.enterLeaderBoardIncrement(bid, userId, score);
    }

}
