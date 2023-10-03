package org.javaboy.tcrelax.exchange.inner.cost.extension;

import org.javaboy.tcrelax.exchange.inner.cost.CostBenefitExtension;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj
 * 免费兑换；
 */
@Component("cfHoliday")
public class CFHolidayCostBenefitExtension implements CostBenefitExtension {

    @Override
    public Integer queryAmount() {
        return 1;
    }

    @Override
    public boolean deductUserAsset(Integer amount, String uid) {
        return true;
    }

    @Override
    public void increaseAmount(Integer amount, String uid) {

    }
}
