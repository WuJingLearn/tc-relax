package org.javaboy.tcrelax.leadboard.extension;

/**
 * @author:majin.wj
 */
public interface ILeaderBoardExtension {


    String getBid(String bizType, Long userId);

    Long getTotalScore(String bid, Long userId, Long score);
}
