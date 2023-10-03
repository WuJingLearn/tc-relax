package org.javaboy.tcrelax.exchange.manager;

import org.javaboy.tcrelax.common.utils.DateUtils;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.config.ExchangeBenefitConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj
 */
@Component
public class ExchangeConfigMemoryLoader implements ExchangeConfigLoader {

    private Map<String, ExchangeActivityConfig> configRepository = new HashMap<>();

    @Override
    public void initExchangeConfig() {
        ExchangeActivityConfig config = new ExchangeActivityConfig();
        config.setScene("cf_holiday");
        config.setActivityName("穿越火线十一黄金周兑换得好礼");
        config.setStartTime(DateUtils.formatDate("2023-10-01 OO:OO:00"));
        config.setStartTime(DateUtils.formatDate("2023-10-07 23:59:59"));

        List<ExchangeBenefitConfig> benefitConfigList = new ArrayList<>();
        ExchangeBenefitConfig akBenefit = new ExchangeBenefitConfig();
        akBenefit.setBenefitCode("ak47");
        benefitConfigList.add(akBenefit);

        ExchangeBenefitConfig m4Benefit = new ExchangeBenefitConfig();
        m4Benefit.setBenefitCode("m4a1");
        benefitConfigList.add(m4Benefit);

        config.setBenefitConfigList(benefitConfigList);

        configRepository.put("cf_holiday", config);
    }

    @Override
    public ExchangeActivityConfig getExchangeConfig(String scene) {
        return configRepository.get(scene);
    }
}
