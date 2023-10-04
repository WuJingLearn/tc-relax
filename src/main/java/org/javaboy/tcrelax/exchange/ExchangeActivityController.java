package org.javaboy.tcrelax.exchange;

import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.dto.BenefitDTO;
import org.javaboy.tcrelax.exchange.manager.ExchangeConfigLoader;
import org.javaboy.tcrelax.exchange.service.ExchangeCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:majin.wj
 */
@RestController
public class ExchangeActivityController {

    @Autowired
    private ExchangeCenterService exchangeService;

    @Autowired
    private ExchangeConfigLoader configLoader;

    @GetMapping("/queryExchangeConfig")
    public ExchangeActivityConfig queryActivity(String scene){
        ExchangeActivityConfig exchangeConfig = configLoader.getExchangeConfig(scene);
        return exchangeConfig;
    }

    @GetMapping("/exchange")
    public TcResult<BenefitDTO> exchange(String uid){
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setUid(uid);
        exchangeRequest.setScene("cfHoliday");
        exchangeRequest.setBenefitCode("ak47");
        return exchangeService.exchange(exchangeRequest);

    }

}
