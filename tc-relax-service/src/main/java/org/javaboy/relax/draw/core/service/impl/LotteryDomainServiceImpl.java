package org.javaboy.relax.draw.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.relax.common.TcResult;
import org.javaboy.relax.dal.dataobject.draw.DrawRecordDO;
import org.javaboy.relax.draw.core.asset.UserAssetService;
import org.javaboy.relax.draw.core.check.handler.LotteryCheckHandlerChain;
import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.entity.AwardInfo;
import org.javaboy.relax.draw.core.lottery.LotteryService;
import org.javaboy.relax.draw.core.recover.RecoverService;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.core.service.LotteryDomainService;
import org.javaboy.relax.draw.enums.CacheKeyEnum;
import org.javaboy.relax.draw.enums.LotteryResultEnum;
import org.javaboy.relax.draw.exception.LotteryException;
import org.javaboy.relax.insfra.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
@Slf4j
public class LotteryDomainServiceImpl implements LotteryDomainService {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private LotteryCheckHandlerChain checkHandlerChain;

    @Autowired
    private UserAssetService userAssetService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private RecoverService recoverService;

    @Override
    public TcResult<List<AwardInfo>> draw(DrawRequest request) {
        LotteryContext context = new LotteryContext();
        TcResult<List<AwardInfo>> result;
        // 防止同个用户,一下触发多个请求，需要保证抽奖的幂等性
        Lock lock = redisLock.getLock(CacheKeyEnum.LOTTERY_DRAW.getCacheKey(request.getUserId(), request.getActivityId()));
        try {
            if (!lock.tryLock()) {
                // 前端展示操作频繁，稍后重试
                throw new LotteryException(LotteryResultEnum.LOCK_FAIL);
            }
            List<AwardInfo> awardInfos = new ArrayList<>();
            for (int i = 0; i < request.getDrawTimes(); i++) {
                context.setDrawTime(i);
                TcResult<List<AwardInfo>> singleResult = singleDraw(request, context);
                if (singleResult.isSuccess()) {
                    awardInfos.addAll(singleResult.getData());
                }
                // 针对抽奖失败次数；打印日志
                log.warn("activityId:{} userId:{} drawTime:{} is {}", request.getActivityId(), request.getUserId(), i, singleResult.isSuccess() ? "成功" : "失败");
            }
            result = TcResult.success(awardInfos);
        } catch (LotteryException e) {
            result = TcResult.fail(e.getMessage());
        } catch (Exception e) {
            result = TcResult.fail(LotteryResultEnum.SYSTEM_ERROR.getDesc());
        } finally {
            lock.unlock();
        }

        return result;
    }

    private TcResult<List<AwardInfo>> singleDraw(DrawRequest request, LotteryContext context) {
        TcResult<List<AwardInfo>> result = null;
        try {
            checkHandlerChain.check(request, context);
            // 幂等
            if (context.isRepeated()) {
                return TcResult.success(getAwardInfo(context.getDrawRecord()));
            }
            // 扣减用户权益
            userAssetService.deductUserAsset(request, context);
            // 如果没有抽到，会抛异常
            lotteryService.draw(request, context);
            // 发放奖励;
            result = TcResult.success(null);

        } catch (IllegalArgumentException e) {
            result = TcResult.fail(LotteryResultEnum.ILLEGAL_ARGUMENTS.getCode(), e.getMessage());
        } catch (LotteryException e) {
            // LotteryException为可预知的异常
            result = TcResult.fail(e.getErrorCode(), e.getMessage());
        } catch (Throwable e) {
            // 系统异常
            result = TcResult.fail(LotteryResultEnum.SYSTEM_ERROR.getCode(), e.getMessage());
        } finally {
            if (!result.isSuccess()) {

            }
        }

        return result;
    }

    private List<AwardInfo> getAwardInfo(List<DrawRecordDO> drawRecords) {
        if (drawRecords != null) {
            return drawRecords.stream().map(record -> {
                AwardInfo awardInfo = new AwardInfo();
                awardInfo.setAwardName(record.getAwardName());
                awardInfo.setAwardType(record.getAwardType());
                awardInfo.setAwardAmount(record.getAwardAmount());
                // todo 补充奖励信息
                return awardInfo;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();

    }

    @Override
    public TcResult<List<AwardInfo>> preview(DrawRequest request) {
        return null;
    }
}
