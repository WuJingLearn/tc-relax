package org.javaboy.relax.draw.core.lottery.group;


import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.DrawItemConfigEntity;

/**
 * @author:majin.wj
 * 抽奖组策略
 */
public interface LotteryGroupStrategy {
    DrawItemConfigEntity draw(DrawRequest request, LotteryContext context);
}
