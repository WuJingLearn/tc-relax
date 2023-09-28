package org.javaboy.tcrelax.draw.core.lottery.group;

import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.javaboy.tcrelax.draw.core.lottery.item.ProbabilityItemStrategy;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;
import org.javaboy.tcrelax.draw.entity.DrawGroupConfigEntity;
import org.javaboy.tcrelax.draw.entity.DrawItemConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:majin.wj 优先级策略
 */
@Component
public class PriorityGroupStrategy implements LotteryGroupStrategy {

    @Autowired
    private ProbabilityItemStrategy itemStrategy;

    @Override
    public DrawItemConfigEntity draw(DrawRequest request, LotteryContext context) {
        List<DrawGroupConfigEntity> drawGroupConfigs = context.getDrawActivityConfigEntity().getDrawGroupConfigs();
        for (DrawGroupConfigEntity drawGroupConfig : drawGroupConfigs) {
            // 按照优先级,知道抽取到一个奖励位置
            DrawItemConfigEntity entity = itemStrategy.draw(request, context, drawGroupConfig);
            if (entity != null) {
                return entity;
            }
        } return null;
    }


}
