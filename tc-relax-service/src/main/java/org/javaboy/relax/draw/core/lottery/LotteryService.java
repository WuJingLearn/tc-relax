package org.javaboy.relax.draw.core.lottery;


import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.lottery.group.PriorityGroupStrategy;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.DrawItemConfigEntity;
import org.javaboy.relax.draw.enums.LotteryResultEnum;
import org.javaboy.relax.draw.exception.LotteryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
@Service
public class LotteryService {

    @Autowired
    private Map<String, PriorityGroupStrategy> groupStrategyMap = new HashMap<>();

    public DrawItemConfigEntity draw(DrawRequest request, LotteryContext context){
        String drawMode = context.getDrawActivityConfigEntity().getDrawMode();
        PriorityGroupStrategy groupStrategy = groupStrategyMap.get(drawMode);
        DrawItemConfigEntity itemEntity = groupStrategy.draw(request, context);
        if(itemEntity == null) {
            throw new LotteryException(LotteryResultEnum.INVENTORY_NOT_ENOUGH);
        }
        context.setDrwaItemConfigEntity(itemEntity);
        return itemEntity;
    }


}
