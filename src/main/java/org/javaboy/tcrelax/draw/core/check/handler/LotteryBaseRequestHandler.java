package org.javaboy.tcrelax.draw.core.check.handler;

import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;
import org.javaboy.tcrelax.draw.enums.LotteryOptionEnum;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author:majin.wj
 */
@Order(0)
@Component
public class
LotteryBaseRequestHandler implements LotteryCheckHandler {

    /**
     * 基本的参数校验
     * @param request
     * @param context
     * @return
     */
    @Override
    public boolean check(DrawRequest request, LotteryContext context) {
        Assert.notNull(request, "request is null");
        Assert.notNull(request.getUserId(),"userid is null");
        Assert.notNull(request.getActivityId(),"activityId is null");
        return true;
    }

    @Override
    public boolean supportOption(String scene) {
        return LotteryOptionEnum.DRAW.isSameOption(scene) || LotteryOptionEnum.PREVIEW.isSameOption(scene);
    }
}
