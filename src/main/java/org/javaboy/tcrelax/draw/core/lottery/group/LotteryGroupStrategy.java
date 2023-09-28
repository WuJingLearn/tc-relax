package org.javaboy.tcrelax.draw.core.lottery.group;

import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;
import org.javaboy.tcrelax.draw.entity.DrawItemConfigEntity;

/**
 * @author:majin.wj
 * 抽奖组策略
 */
public interface LotteryGroupStrategy {
    DrawItemConfigEntity draw(DrawRequest request, LotteryContext context);
}
