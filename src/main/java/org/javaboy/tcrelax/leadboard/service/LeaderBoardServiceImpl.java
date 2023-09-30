package org.javaboy.tcrelax.leadboard.service;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.common.exceptions.BizException;
import org.javaboy.tcrelax.leadboard.LeaderBoardService;
import org.javaboy.tcrelax.leadboard.check.LeaderBoardRuleChecker;
import org.javaboy.tcrelax.leadboard.config.LeaderBoardConfig;
import org.javaboy.tcrelax.leadboard.extension.LeaderBoardExtension;
import org.javaboy.tcrelax.leadboard.manager.LeaderBoardConfigRepository;
import org.javaboy.tcrelax.leadboard.memeber.Member;
import org.javaboy.tcrelax.leadboard.repository.LeaderBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:majin.wj
 */
@Slf4j
@Component
public class LeaderBoardServiceImpl implements LeaderBoardService {


    @Autowired
    private LeaderBoardConfigRepository leaderBoardConfigRepository;

    @Autowired
    private LeaderBoardExtension leaderBoardExt;

    @Autowired
    private LeaderBoardRepository leaderBoardRepository;


    /**
     * @param bizType 榜单id；
     *                比如在斗地主入榜时：需要先计算出该用户在哪里榜单中；
     * @param userId
     * @param score
     */
    @Override
    public void enterLeaderBoardIncrement(String bizType, Long userId, Long score) throws BizException {
        checkLeaderBoard(bizType);
        // 获取榜单id; 不同的业务类型,榜单id可能会不一样。比如说排行榜有分榜的配置
        String bid = leaderBoardExt.getBid(bizType, userId);
        if (bid == null) {
            log.error("没有获取到有效的排行榜");
            return;
        }
        Long totalScore = leaderBoardExt.getTotalScore(bid, userId, score);
        leaderBoardRepository.enterLeaderBoard(bid, userId, totalScore);
    }

    /**
     * 不记录历史分数，对于同一个用户，如果后面进入的分数比之前的小，会覆盖掉之前大的分数；
     *
     * @param bizType
     * @param userId
     * @param score
     */
    @Override
    public void enterLeaderBoardCurrent(String bizType, Long userId, Long score) {
        checkLeaderBoard(bizType);
        String bid = leaderBoardExt.getBid(bizType, userId);
        if (bid == null) {
            log.error("没有获取到有效的排行榜");
            return;
        }
        leaderBoardRepository.enterLeaderBoard(bid, userId, score);
    }

    @Override
    public List<Member> queryTopN(String bizType, Integer topN, Long uerId) {
        checkLeaderBoard(bizType);
        // 获取用户所在的逻辑榜单；
        String bid = leaderBoardExt.getBid(bizType, uerId);
        return leaderBoardRepository.queryTopN(bid, topN);
    }

    @Override
    public List<Member> queryTopN(String leaderBoardKey, Integer topN) {
        return leaderBoardRepository.queryTopN(leaderBoardKey, topN);
    }


    @Override
    public Long queryScore(String bizType, Long userId) {
        checkLeaderBoard(bizType);
        String bid = leaderBoardExt.getBid(bizType, userId);
        return leaderBoardRepository.queryScore(bid, userId);
    }


    private void checkLeaderBoard(String bizType) {
        LeaderBoardConfig leaderBoardConfig = leaderBoardConfigRepository.getLeaderBoardConfig(bizType);
        LeaderBoardRuleChecker.check(leaderBoardConfig);
    }
}
