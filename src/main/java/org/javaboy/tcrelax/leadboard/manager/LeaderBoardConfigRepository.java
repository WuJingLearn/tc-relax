package org.javaboy.tcrelax.leadboard.manager;

import org.javaboy.tcrelax.leadboard.config.LeaderBoardConfig;

/**
 * @author:majin.wj
 */
public interface LeaderBoardConfigRepository {

    LeaderBoardConfig getLeaderBoardConfig(String bizType);

}
