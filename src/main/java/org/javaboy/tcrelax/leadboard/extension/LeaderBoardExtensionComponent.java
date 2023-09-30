package org.javaboy.tcrelax.leadboard.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author:majin.wj
 */
@Component
public class LeaderBoardExtensionComponent implements ILeaderBoardExtension {

    /**
     * bean名称需要写出扩展名
     */
    @Autowired
    private Map<String,ILeaderBoardExtension> leaderBoardExtensionMap = new HashMap<>();

    @PostConstruct
    public void init(){
        System.out.println("扩展点有："+leaderBoardExtensionMap);
    }


    @Override
    public String getBid(String bizType, Long userId) {
        return Optional.ofNullable(leaderBoardExtensionMap.get(bizType)).map(e->e.getBid(bizType,userId)).orElse(null);
    }

    @Override
    public Long getTotalScore(String bid, Long userId, Long score) {
        String bizType = getBizType(bid);
        return Optional.ofNullable(leaderBoardExtensionMap.get(bizType)).map(e->e.getTotalScore(bid,userId,score)).orElse(0L);
    }

    private String getBizType(String bid) {
        return bid.split("_")[0];
    }


}
