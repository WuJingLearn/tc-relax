package org.javaboy.relax.draw.core.lottery.item;


import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.lottery.LotteryUtil;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.DrawGroupConfigEntity;
import org.javaboy.relax.draw.entity.DrawItemConfigEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
public class ProbabilityItemStrategy {

    /**
     * @param request
     * @return
     */
    public DrawItemConfigEntity draw(DrawRequest request, LotteryContext context, DrawGroupConfigEntity entity) {
        List<DrawItemConfigEntity> drawItems = entity.getDrawItems();
        // 根据概率抽到商品
        while (!drawItems.isEmpty()) {
            Map<DrawItemConfigEntity, Long> itemRatios = drawItems.stream().collect(Collectors.toMap((item) -> item, item -> item.getRatio()));
            LotteryUtil<DrawItemConfigEntity> lotteryUtil = new LotteryUtil<>(itemRatios);
            DrawItemConfigEntity itemEntity = lotteryUtil.lottery();
            // 库存扣减成功,返回奖项
            if(useInventory(itemEntity)) {
                return itemEntity;
            }
            // 移除该奖品,继续抽奖
            drawItems.remove(itemEntity);

        }
        return null;
    }

    public boolean useInventory(DrawItemConfigEntity item){
        return true;

    }
}
