package org.javaboy.tcrelax.draw.core.recover;

import org.checkerframework.checker.units.qual.C;
import org.javaboy.tcrelax.draw.core.context.LotteryContext;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj 异常后的补偿措施
 */
@Component
public class RecoverService {


    public void recover(LotteryContext context) {
        if (context.isDeductUserAsset()) {

        }

        if (context.isDeductInventory()) {

        }
    }

}
