package org.javaboy.relax.leadboard.repository;

import org.javaboy.relax.leadboard.memeber.Member;

import java.util.List;

/**
 * @author:majin.wj
 */

public interface LeaderBoardRepository {

    void enterLeaderBoard(String bid, Long uid, Long score);


    Long queryScore(String bid,Long uid);

    /**
     * 查询用户所在榜单topN
     * @param bid
     * @param topN
     * @return
     */
    List<Member> queryTopN(String bid,Integer topN);
}
