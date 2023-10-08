package org.javaboy.relax.exchange.inner.cost;

import org.javaboy.relax.common.exceptions.BizException;
import org.javaboy.relax.common.exceptions.ExceptionEnum;
import org.javaboy.relax.exchange.ExchangeContext;
import org.javaboy.relax.exchange.config.ExchangeCostConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:majin.wj
 */
@Component
public class CostBenefitServiceImpl implements CostBenefitService, ApplicationContextAware {

    private Map<String, CostBenefitExtension> extensions = new HashMap<>();

    @Override
    public void checkUserAsset(ExchangeContext context) {
        ExchangeCostConfig costConfig = context.getBenefitConfig().getCostConfig();
        // 兑换不需要什么条件
        if (costConfig == null) {
            return;
        }
        String costType = costConfig.getType();
        // 自定义兑换条件
        if (ExchangeCostConfig.CostType.isCustom(costType)) {
            CostBenefitExtension customExtension = findCustomExtension(context.getScene());
            if (customExtension == null) {
                throw new BizException("No available extension", "兑换消耗配置没有找到具体的扩展点");
            }
            Integer amount = customExtension.queryAmount(context.getUserId());
            // 兑换不满足条件
            if (amount < costConfig.getAmount()) {
                throw new BizException(ExceptionEnum.EXCHANGE_NO_ENOUGH_COST);
            }
        } else if (ExchangeCostConfig.CostType.UNIFIED.equals(costType)) {
            // 统一权益类型，暂时不实现
            System.out.println("统一权益方法");
        }

    }

    @Override
    public boolean deductUserAsset(ExchangeContext context) {
        ExchangeCostConfig costConfig = context.getBenefitConfig().getCostConfig();
        // 兑换不需要什么条件
        if (costConfig == null) {
            return true;
        }

        String costType = costConfig.getType();
        // 自定义兑换条件
        if (ExchangeCostConfig.CostType.isCustom(costType)) {
            CostBenefitExtension customExtension = findCustomExtension(context.getScene());
            if (customExtension == null) {
                throw new BizException("No available extension", "兑换消耗配置没有找到具体的扩展点");
            }
            boolean deductResult = customExtension.deductUserAsset(costConfig.getAmount(), context.getUserId());
            // 兑换不满足条件
            if (!deductResult) {
                throw new BizException(ExceptionEnum.EXCHANGE_NO_ENOUGH_COST);
            }
        } else if (ExchangeCostConfig.CostType.UNIFIED.equals(costType)) {
            // 统一权益类型，暂时不实现
            System.out.println("统一权益方法");
        }
        return true;
    }

    @Override
    public void increaseUserAsset(ExchangeContext context) {
        ExchangeCostConfig costConfig = context.getBenefitConfig().getCostConfig();
        if (costConfig == null) {
            return;
        }

        String costType = costConfig.getType();
        // 自定义兑换条件
        if (ExchangeCostConfig.CostType.isCustom(costType)) {
            CostBenefitExtension customExtension = findCustomExtension(context.getScene());
            if (customExtension == null) {
                throw new BizException("No available extension", "兑换消耗配置没有找到具体的扩展点");
            }
            // 增加用户权益
            customExtension.increaseAmount(costConfig.getAmount(),context.getUserId());
        } else if (ExchangeCostConfig.CostType.UNIFIED.equals(costType)) {
            // 统一权益类型，暂时不实现
            System.out.println("统一权益方法");
        }
    }


    private CostBenefitExtension findCustomExtension(String scene) {
        // scene的名称需要作为bean的名称
        return extensions.get(scene);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.extensions = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, CostBenefitExtension.class);

    }
}
