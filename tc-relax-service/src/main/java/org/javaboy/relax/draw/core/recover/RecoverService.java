package org.javaboy.relax.draw.core.recover;


import org.javaboy.relax.draw.core.context.LotteryContext;
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
