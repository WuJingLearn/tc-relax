package org.javaboy.tcrelax.exchange.service;

import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.common.exceptions.BizException;
import org.javaboy.tcrelax.common.exceptions.ExceptionEnum;
import org.javaboy.tcrelax.common.utils.DateUtils;
import org.javaboy.tcrelax.exchange.ExchangeContext;
import org.javaboy.tcrelax.exchange.ExchangeRequest;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeBenefitConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeInventoryConfig;
import org.javaboy.tcrelax.exchange.dto.ActivityDetailDTO;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;
import org.javaboy.tcrelax.exchange.dto.BenefitDTO;
import org.javaboy.tcrelax.exchange.inner.cost.CostBenefitService;
import org.javaboy.tcrelax.exchange.inner.fatigue.FatigueService;
import org.javaboy.tcrelax.exchange.inner.inventory.InventoryService;
import org.javaboy.tcrelax.exchange.inner.record.ExchangeRecordService;
import org.javaboy.tcrelax.exchange.manager.ExchangeConfigLoader;
import org.javaboy.tcrelax.insfra.redis.RedisLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:majin.wj
 */
@Slf4j
@Component
public class ExchangeCenterServiceImpl implements ExchangeCenterService {

    @Autowired
    private ExchangeConfigLoader configLoader;

    @Autowired
    private FatigueService fatigueService;

    @Autowired
    private ExchangeRecordService recordService;

    @Autowired
    private CostBenefitService costBenefitService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private RedisLock redisLock;

    /**
     * 0.参数,活动有效性校验
     * 1.幂等校验 outId
     * 2.疲劳度校验
     * 活动维度；一个活动最多兑换几次
     * 商品维度；一个商品最多兑换几次
     * <p>
     * 3.当前库存校验
     * 4.扣减库存
     * 5.
     *
     * @param request
     * @return
     */
    @Override
    public TcResult<BenefitDTO> exchange(ExchangeRequest request) {
        TcResult<BenefitDTO> response = null;
        checkRequest(request);
        String lockKey = String.format("exchange_%s_%s", request.getScene(), request.getUid());
        RLock lock = redisLock.getLock(lockKey);
        ExchangeContext context = buildExchangeContext(request);
        try {
            if (!lock.tryLock(3, 3, TimeUnit.SECONDS)) {
                throw new BizException(ExceptionEnum.GET_LOCK_FAIL);
            }
            // 活动级别疲劳度
            fatigueService.checkActivityFatigue(context);
            // 商品级别疲劳度
            fatigueService.checkBenefitFatigue(context);
            // 校验兑换满足的条件。比如需要多少积分才能兑换一次；
            costBenefitService.checkUserAsset(context);
            // 校验库存是否充足
            checkBenefitInventory(context);
            // 扣减兑换条件
            costBenefitService.deductUserAsset(context);
            // 扣减库存
            inventoryService.deductBenefitInventory(context);
            // 发放奖励,这里只记录到数据库中
            recordService.addUserRecord(buildAwardRecord(context, request));
        } catch (BizException bizException) {
            if (context.isDeductUserAssetResult()) {
                costBenefitService.increaseUserAsset(context);
            }
            if (context.isDeductInventoryResult()) {
                inventoryService.increaseBenefitInventory(context);
            }
            response = TcResult.fail(bizException.getCode(), bizException.getMessage());
        } catch (Throwable t) {
            log.error("兑换出错,scene:{},benefitCode:{},uid:{},error:{}", request.getScene(), request.getBenefitCode(), request.getUid(), t.getMessage(), t);
            response = TcResult.fail(ExceptionEnum.SYSTEM_ERROR.getCode(), t.getMessage());
        } finally {
            lock.unlock();
        }
        return response;
    }

    /**
     * 查看活动
     *
     * @param request
     * @return
     */
    @Override
    public TcResult<ActivityDetailDTO> preview(ExchangeRequest request) {
        ExchangeActivityConfig exchangeConfig = configLoader.getExchangeConfig(request.getScene());

        ActivityDetailDTO activityDetailDTO = new ActivityDetailDTO();
        activityDetailDTO.setScene(exchangeConfig.getScene());
        activityDetailDTO.setActivityName(exchangeConfig.getActivityName());


        List<ExchangeBenefitConfig> benefitConfigList = exchangeConfig.getBenefitConfigList();
        List<BenefitDTO> benefitDTOS = new ArrayList<>();

        for (ExchangeBenefitConfig benefitConfig : benefitConfigList) {
            BenefitDTO benefitDTO = new BenefitDTO();
            if (!benefitConfig.isUseInventory()) {
                // 不限量
                benefitDTO.setTotalLimit(false);
                benefitDTO.setHourAmount(true);
            } else {
                benefitDTO.setTotalLimit(true);
                ExchangeInventoryConfig inventoryConfig = benefitConfig.getInventoryConfig();
                String inventoryStrategy = inventoryConfig.getInventoryStrategy();
                // 总库存
                Integer totalAmount = inventoryConfig.getTotalAmount();
                benefitDTO.setTotalAmount(totalAmount);
                // 当前库存
                Integer amount = inventoryService.queryBenefitInventory(request.getScene(), benefitConfig.getBenefitCode(), inventoryStrategy);
                // 当前小时是否还有库存
                benefitDTO.setHourAmount(amount > 0);
            }
            benefitDTO.setUrl(benefitDTO.getUrl());
            benefitDTO.setBenefitCode(benefitDTO.getBenefitCode());
            benefitDTOS.add(benefitDTO);
        }
        activityDetailDTO.setBenefits(benefitDTOS);
        return TcResult.success(activityDetailDTO);
    }


    @Override
    public TcResult<List<AwardRecordDTO>> queryExchangeRecord(ExchangeRequest request) {
        return null;
    }

    private ExchangeContext buildExchangeContext(ExchangeRequest request) {
        ExchangeActivityConfig activityConfig = configLoader.getExchangeConfig(request.getScene());
        ExchangeContext context = new ExchangeContext();
        context.setUserId(request.getUid());
        context.setRequest(request);
        context.setActivityConfig(activityConfig);
        ExchangeBenefitConfig benefitConfig = activityConfig.getBenefitConfigList().stream()
                .filter(benefit -> request.getBenefitCode().equals(benefit.getBenefitCode()))
                .findFirst().orElse(null);
        if (benefitConfig == null) {
            throw new BizException(ExceptionEnum.BENEFIT_NOT_EXISTS);
        }
        context.setBenefitConfig(benefitConfig);
        return context;
    }

    private void checkRequest(ExchangeRequest request) {
        Assert.notNull(request, "request is null");
        Assert.notNull(request.getScene(), "scene is null");
        ExchangeActivityConfig exchangeConfig = configLoader.getExchangeConfig(request.getScene());
        if (exchangeConfig == null) {
            throw new BizException(ExceptionEnum.ACTIVITY_NOT_EXISTS);
        }
        if (!DateUtils.between(new Date(), exchangeConfig.getStartTime(), exchangeConfig.getEndTime())) {
            throw new BizException(ExceptionEnum.ACTIVITY_TIME_ILLEGAL);
        }
    }


    private void checkBenefitInventory(ExchangeContext context) {
        ExchangeBenefitConfig benefitConfig = context.getBenefitConfig();
        // 不限量库存无需校验
        if (!benefitConfig.isUseInventory()) {
            return;
        }
        String inventoryStrategy = benefitConfig.getInventoryConfig().getInventoryStrategy();
        Integer i = inventoryService.queryBenefitInventory(context.getScene(), benefitConfig.getBenefitCode(), inventoryStrategy);
        // 没有库存;
        if (i <= 0) {
            throw new BizException(ExceptionEnum.BENEFIT_INVENTORY_NOT_ENOUGH);
        }
    }

    private AwardRecordDTO buildAwardRecord(ExchangeContext context, ExchangeRequest request) {
        AwardRecordDTO awardRecordDTO = new AwardRecordDTO();
        awardRecordDTO.setScene(request.getScene());
        ExchangeBenefitConfig benefitConfig = context.getBenefitConfig();
        String url = benefitConfig.getUrl();
        String benefitCode = benefitConfig.getBenefitCode();
        awardRecordDTO.setBenefitCode(benefitCode);
        awardRecordDTO.setUrl(url);
        awardRecordDTO.setCreateTime(new Date());
        awardRecordDTO.setUid(request.getUid());
        awardRecordDTO.setOutId(request.getOutId());
        return awardRecordDTO;
    }


}
