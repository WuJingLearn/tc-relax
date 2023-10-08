package org.javaboy.relax.leadboard.manager;

import org.javaboy.relax.leadboard.config.LeaderBoardConfig;

/**
 * @author:majin.wj
 */
public interface LeaderBoardConfigRepository {

    LeaderBoardConfig getLeaderBoardConfig(String bizType);

}
