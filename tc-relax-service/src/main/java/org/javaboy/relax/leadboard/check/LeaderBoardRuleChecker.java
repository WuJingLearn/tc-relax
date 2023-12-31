package org.javaboy.relax.leadboard.check;

import org.javaboy.relax.common.exceptions.BizException;
import org.javaboy.relax.common.exceptions.ExceptionEnum;
import org.javaboy.relax.leadboard.config.LeaderBoardConfig;
import org.javaboy.relax.leadboard.config.Period;

import java.util.Calendar;
import java.util.Date;

/**
 * @author:majin.wj
 */
public class LeaderBoardRuleChecker {

    public static void check(LeaderBoardConfig config) {
        if (config == null) {
            throw new BizException(ExceptionEnum.ACTIVITY_NOT_EXISTS);
        }
        if (Period.isCustom(config.getType())) {
            Date start = config.getStart();
            Date end = config.getEnd();
            long nowTime = Calendar.getInstance().getTime().getTime();
            if (nowTime < start.getTime() || nowTime > end.getTime()) {
                throw new BizException(ExceptionEnum.ACTIVITY_TIME_ILLEGAL);
            }
        }
    }

}
