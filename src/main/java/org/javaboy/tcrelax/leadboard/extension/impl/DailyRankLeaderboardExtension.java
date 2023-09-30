package org.javaboy.tcrelax.leadboard.extension.impl;

import org.checkerframework.checker.units.qual.C;
import org.javaboy.tcrelax.leadboard.extension.ILeaderBoardExtension;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj
 */
@Component
public class DailyRankLeaderboardExtension implements ILeaderBoardExtension {
    @Override
    public String getBid(String bizType, Long userId) {
        return null;
    }

    @Override
    public Long getTotalScore(String bid, Long userId, Long score) {
        return null;
    }
}
