package org.javaboy.tcrelax.exchange.inner.fatigue;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.common.exceptions.BizException;
import org.javaboy.tcrelax.common.exceptions.ExceptionEnum;
import org.javaboy.tcrelax.common.utils.DateUtils;
import org.javaboy.tcrelax.exchange.ExchangeContext;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeFatigueConfig;
import org.javaboy.tcrelax.exchange.config.FatigueType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;

/**
 * @author:majin.wj
 */
@Slf4j
@Component
public class FatigueServiceImpl implements FatigueService {

    /**
     * 活动疲劳度all类型 key，活动期间
     */
    private static final String ACTIVITY_FATIGUE_KEY = "activity_all_fatigue_%s_%s";

    /**
     * 活动疲劳度,天维度
     */
    private static final String ACTIVITY_FATIGUE_DAY_KEY = "activity_day_fatigue_%s_%s_%s";

    /**
     * 活动疲劳度,周维度
     */
    private static final String ACTIVITY_FATIGUE_WEEK_KEY = "activity_week_fatigue_%s_%s_%s";

    /**
     * scene_benefitCode_uid
     */
    private static final String BENEFIT_FATIGUE_KEY = "benefit_fatigue_%s_%s_%s";
    /**
     * scene_benefitCode_day_uid
     */
    private static final String BENEFIT_FATIGUE_DAY_KEY = "benefit_fatigue_%s_%s_%s_%s";
    /**
     * scene_benefitCode_first_uid
     */
    private static final String BENEFIT_FATIGUE_WEEK_KEY = "benefit_fatigue_%s_%s_%s_%s";

    @Autowired
    private Jedis jedis;

    @Override
    public void checkActivityFatigue(ExchangeContext exchangeContext) {

        ExchangeFatigueConfig fatigueConfig = exchangeContext.getActivityConfig().getFatigueConfig();
        if (fatigueConfig == null) {
            return;
        }
        ExchangeActivityConfig activityConfig = exchangeContext.getActivityConfig();
        String fatigueKey = null;


        switch (FatigueType.valueOf(fatigueConfig.getFatigueType().toUpperCase())) {
            case ALL:
                fatigueKey = String.format(ACTIVITY_FATIGUE_KEY, activityConfig.getScene(), exchangeContext.getUserId());
                break;
            case DAY:
                fatigueKey = String.format(ACTIVITY_FATIGUE_DAY_KEY, activityConfig.getScene(), DateUtils.dateStr(), exchangeContext.getUserId());
                break;
            case WEEK:
                fatigueKey = String.format(ACTIVITY_FATIGUE_WEEK_KEY, activityConfig.getScene(), DateUtils.getFistDayOfWeak(), exchangeContext.getUserId());
                break;
        }

        Integer currentTime = Optional.ofNullable(jedis.get(fatigueKey)).map(Integer::valueOf).orElse(0);
        // 超过疲劳度
        if (currentTime >= fatigueConfig.getAmount()) {
            log.info("scene:{} userId:{},fatigueType:{},currentTime:{},fatigue:{} 达到活动级别疲劳度限制",
                    activityConfig.getActivityName(), exchangeContext.getUserId(), fatigueConfig.getFatigueType(),
                    currentTime, fatigueConfig.getAmount());
            throw new BizException(ExceptionEnum.ACTIVITY_FATIGUE.getCode(), "当前周期兑换达到最大限制");
        }
    }

    @Override
    public void checkBenefitFatigue(ExchangeContext exchangeContext) {
        ExchangeFatigueConfig fatigueConfig = exchangeContext.getBenefitConfig().getFatigueConfig();
        if (fatigueConfig == null) {
            return;
        }
        ExchangeActivityConfig activityConfig = exchangeContext.getActivityConfig();
        String benefitCode = exchangeContext.getRequest().getBenefitCode();
        String fatigueKey = null;
        switch (FatigueType.valueOf(fatigueConfig.getFatigueType().toUpperCase())) {
            case ALL:
                fatigueKey = String.format(BENEFIT_FATIGUE_KEY, activityConfig.getScene(), benefitCode, exchangeContext.getUserId());
                break;
            case DAY:
                fatigueKey = String.format(BENEFIT_FATIGUE_DAY_KEY, activityConfig.getScene(), benefitCode, DateUtils.dateStr(), exchangeContext.getUserId());
                break;
            case WEEK:
                fatigueKey = String.format(BENEFIT_FATIGUE_WEEK_KEY, activityConfig.getScene(), benefitCode, DateUtils.getFistDayOfWeak(), exchangeContext.getUserId());
                break;
        }

        Integer currentTime = Optional.ofNullable(jedis.get(fatigueKey)).map(Integer::valueOf).orElse(0);
        // 超过疲劳度
        if (currentTime >= fatigueConfig.getAmount()) {
            log.info("scene:{},benefitCode:{},userId:{},fatigueType:{},currentTime:{},fatigue:{} 达到权益级别疲劳度限制",
                    activityConfig.getActivityName(), benefitCode, exchangeContext.getUserId(), fatigueConfig.getFatigueType(),
                    currentTime, fatigueConfig.getAmount());
            throw new BizException(ExceptionEnum.BENEFIT_FATIGUE.getCode(), "当前周期商品兑换到达最大限制");

        }
    }

    @Override
    public void recordActivityFatigue(ExchangeContext exchangeContext) {
        ExchangeFatigueConfig fatigueConfig = exchangeContext.getActivityConfig().getFatigueConfig();
        if (fatigueConfig == null) {
            return;
        }
        ExchangeActivityConfig activityConfig = exchangeContext.getActivityConfig();
        String fatigueKey = null;


        switch (FatigueType.valueOf(fatigueConfig.getFatigueType().toUpperCase())) {
            case ALL:
                fatigueKey = String.format(ACTIVITY_FATIGUE_KEY, activityConfig.getScene(), exchangeContext.getUserId());
                break;
            case DAY:
                fatigueKey = String.format(ACTIVITY_FATIGUE_DAY_KEY, activityConfig.getScene(), DateUtils.dateStr(), exchangeContext.getUserId());
                break;
            case WEEK:
                fatigueKey = String.format(ACTIVITY_FATIGUE_WEEK_KEY, activityConfig.getScene(), DateUtils.getFistDayOfWeak(), exchangeContext.getUserId());
                break;
        }
        Long incr = jedis.incr(fatigueKey);
        jedis.expire(fatigueKey, DateUtils.period(activityConfig.getStartTime(), activityConfig.getEndTime()));
        log.info("活动:{}, uid:{},记录一次活动级别疲劳度,当前疲劳度:{}", activityConfig.getScene(), exchangeContext.getUserId(), incr);
    }

    @Override
    public void recordBenefitFatigue(ExchangeContext exchangeContext) {
        ExchangeFatigueConfig fatigueConfig = exchangeContext.getBenefitConfig().getFatigueConfig();
        if (fatigueConfig == null) {
            return;
        }
        ExchangeActivityConfig activityConfig = exchangeContext.getActivityConfig();
        String benefitCode = exchangeContext.getRequest().getBenefitCode();
        String fatigueKey = null;
        switch (FatigueType.valueOf(fatigueConfig.getFatigueType().toUpperCase())) {
            case ALL:
                fatigueKey = String.format(BENEFIT_FATIGUE_KEY, activityConfig.getScene(), benefitCode, exchangeContext.getUserId());
                break;
            case DAY:
                fatigueKey = String.format(BENEFIT_FATIGUE_DAY_KEY, activityConfig.getScene(), benefitCode, DateUtils.dateStr(), exchangeContext.getUserId());
                break;
            case WEEK:
                fatigueKey = String.format(BENEFIT_FATIGUE_WEEK_KEY, activityConfig.getScene(), benefitCode, DateUtils.getFistDayOfWeak(), exchangeContext.getUserId());
                break;
        }
        Long incr = jedis.incr(fatigueKey);
        jedis.expire(fatigueKey,DateUtils.period(activityConfig.getStartTime(),activityConfig.getEndTime()));
        log.info("活动:{},权益code:{}, uid:{},记录一次权益级别疲劳度,当前疲劳度:{}", activityConfig.getScene(), benefitCode, exchangeContext.getUserId(), incr);
    }
}
