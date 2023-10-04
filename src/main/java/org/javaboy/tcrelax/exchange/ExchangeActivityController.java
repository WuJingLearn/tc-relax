package org.javaboy.tcrelax.exchange;

import net.bytebuddy.description.field.FieldDescription;
import org.javaboy.tcrelax.common.PageResult;
import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.exchange.config.ExchangeActivityConfig;
import org.javaboy.tcrelax.exchange.dto.ActivityDetailDTO;
import org.javaboy.tcrelax.exchange.dto.AwardRecordDTO;
import org.javaboy.tcrelax.exchange.dto.BenefitDTO;
import org.javaboy.tcrelax.exchange.manager.ExchangeConfigLoader;
import org.javaboy.tcrelax.exchange.service.ExchangeCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:majin.wj
 */
@RestController
@CrossOrigin
public class ExchangeActivityController {

    @Autowired
    private ExchangeCenterService exchangeService;

    @Autowired
    private ExchangeConfigLoader configLoader;

    @GetMapping("/queryExchangeConfig")
    public ExchangeActivityConfig queryActivity(String scene) {
        ExchangeActivityConfig exchangeConfig = configLoader.getExchangeConfig(scene);
        return exchangeConfig;
    }

    @GetMapping("/exchange")
    public TcResult<BenefitDTO> exchange(String uid) {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setUid(uid);
        exchangeRequest.setScene("cfHoliday");
        exchangeRequest.setBenefitCode("ak47");
        return exchangeService.exchange(exchangeRequest);
    }

    @GetMapping("/preview")
    public TcResult<ActivityDetailDTO> preview(String scene) {
        ExchangeRequest request = new ExchangeRequest();
        request.setScene(scene);
        return exchangeService.preview(request);
    }

    @GetMapping("/queryExchangeRecord")
    public TcResult<PageResult<List<AwardRecordDTO>>> queryExchangeRecord(String scene, String uid,
                                                                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Assert.isTrue(page > 0, "page必须大于0");
        return exchangeService.queryExchangeRecord(scene, uid, page, pageSize);
    }

}
