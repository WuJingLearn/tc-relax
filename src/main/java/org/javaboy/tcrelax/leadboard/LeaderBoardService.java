package org.javaboy.tcrelax.leadboard;

import org.javaboy.tcrelax.common.exceptions.BizException;
import org.javaboy.tcrelax.leadboard.memeber.Member;

import java.util.List;

/**
 * @author:majin.wj 排行榜服务
 */
public interface LeaderBoardService {


    /**
     * 增量分数进入排行榜，分数可以增加也可以减少;
     * 1.那么用户的历史分数需要保存下来；
     *
     */
    void enterLeaderBoardIncrement(String bizType, Long userId, Long score) throws BizException;


    /**
     * 全量分数进入排行榜
     */
    void enterLeaderBoardCurrent(String bizType, Long userId, Long score);


    /**
     * 查询排行榜前N名成员
     *
     * @param bizType
     * @param topN
     * @return
     */
    List<Member> queryTopN(String bizType, Integer topN,Long userId);

    /**
     * 根据逻辑榜单查询前topN
     * weekRank_huangdi_2023828
     * @param leaderBoardKey
     * @return
     */
    List<Member> queryTopN(String leaderBoardKey,Integer topN);

    /**
     * 查询分数
     *
     * @param bizType
     * @param userId
     * @return
     */
    Long queryScore(String bizType, Long userId);

}
