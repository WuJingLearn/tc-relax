package org.javaboy.relax.draw.core.lottery.group;


import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.lottery.LotteryUtil;
import org.javaboy.relax.draw.core.lottery.item.ProbabilityItemStrategy;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.DrawGroupConfigEntity;
import org.javaboy.relax.draw.entity.DrawItemConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
public class ProbabilityGroupStrategy implements LotteryGroupStrategy {

    @Autowired
    private ProbabilityItemStrategy itemStrategy;

    @Override
    public DrawItemConfigEntity draw(DrawRequest request, LotteryContext context) {
        List<DrawGroupConfigEntity> drawGroupConfigs = context.getDrawActivityConfigEntity().getDrawGroupConfigs();
        while (!drawGroupConfigs.isEmpty()) {
            Map<DrawGroupConfigEntity, Long> groupRatio = drawGroupConfigs.stream().collect(Collectors.toMap(item -> item, item -> item.getRatio()));
            LotteryUtil<DrawGroupConfigEntity> lotteryUtil = new LotteryUtil<>(groupRatio);
            DrawGroupConfigEntity groupConfigEntity = lotteryUtil.lottery();
            DrawItemConfigEntity entity = itemStrategy.draw(request, context, groupConfigEntity);
            if (entity != null) {
                return entity;
            }
            // 没有抽取到，移除该抽奖组，剩余的进行抽奖
            drawGroupConfigs.remove(groupConfigEntity);
        }
        return null;
    }
}
