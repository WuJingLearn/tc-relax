package org.javaboy.tcrelax.leadboard.manager;

import org.javaboy.tcrelax.leadboard.config.LeaderBoardConfig;
import org.javaboy.tcrelax.leadboard.config.Period;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj 基于内存的方式管理配置, 用于测试
 */
@Component
@ConditionalOnProperty(name = "leader.board.config.type", havingValue = "memory")
public class LeaderBoardConfigMemoryRepository implements LeaderBoardConfigRepository {

    private static final String DAILY_RANK_BIZ_TYPE = "dailyRank";
    private static final String WEAK_RANK_BIZ_TYPE = "weekRank";

    private Map<String, LeaderBoardConfig> configMap = new HashMap<>();

    @PostConstruct
    public void initConfig() {
        LeaderBoardConfig dailyRankConfig = new LeaderBoardConfig();
        dailyRankConfig.setType(DAILY_RANK_BIZ_TYPE);
        dailyRankConfig.setBucket(5);
        dailyRankConfig.setLimit(10);
        dailyRankConfig.setPeriod(Period.DAY.name());

        LeaderBoardConfig weekRankConfig = new LeaderBoardConfig();
        weekRankConfig.setType(WEAK_RANK_BIZ_TYPE);
        weekRankConfig.setBucket(5);
        weekRankConfig.setLimit(10);
        weekRankConfig.setPeriod(Period.WEAK.name());
        configMap.put(DAILY_RANK_BIZ_TYPE, dailyRankConfig);
        configMap.put(WEAK_RANK_BIZ_TYPE, weekRankConfig);

    }

    @Override
    public LeaderBoardConfig getLeaderBoardConfig(String bizType) {
        return configMap.get(bizType);
    }
}
