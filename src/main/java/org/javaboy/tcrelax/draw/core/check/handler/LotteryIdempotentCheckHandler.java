package org.javaboy.tcrelax.draw.core.check.handler;

import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.javaboy.tcrelax.draw.core.record.DrawRecordService;
import org.javaboy.tcrelax.draw.core.request.DrawRequest;
import org.javaboy.tcrelax.draw.dataobject.DrawRecordDO;
import org.javaboy.tcrelax.draw.enums.LotteryOptionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author:majin.wj 幂等校验
 */
@Order(2)
@Component
public class LotteryIdempotentCheckHandler implements LotteryCheckHandler {

    @Autowired
    private DrawRecordService drawRecordService;

    @Override
    public boolean check(DrawRequest request, LotteryContext context) {
        List<DrawRecordDO> drawRecordDOS = drawRecordService.queryDrawRecord(request.getUserId(), request.getActivityId());
        context.setDrawRecord(drawRecordDOS);
        return drawRecordDOS != null;
    }

    @Override
    public boolean supportOption(String scene) {
        return LotteryOptionEnum.DRAW.isSameOption(scene);
    }
}
