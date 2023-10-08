package org.javaboy.relax.draw.core.asset;

import org.javaboy.relax.asset.service.IAssetService;
import org.javaboy.relax.asset.service.request.DecreaseAssetRequest;
import org.javaboy.relax.common.TcResult;
import org.javaboy.relax.draw.core.context.LotteryContext;
import org.javaboy.relax.draw.core.request.DrawRequest;
import org.javaboy.relax.draw.entity.CostBenefitEntity;
import org.javaboy.relax.draw.enums.LotteryResultEnum;
import org.javaboy.relax.draw.exception.LotteryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author:majin.wj
 */
@Component
public class UserAssetService {

    @Autowired
    private IAssetService assetService;

    public void deductUserAsset(DrawRequest request, LotteryContext context) {
        CostBenefitEntity costBenefit = context.getDrawActivityConfigEntity().getCostBenefit();
        if (costBenefit == null) {
            return;
        }
        String awardType = costBenefit.getAwardType();
        Integer amount = costBenefit.getAmount();

        DecreaseAssetRequest decreaseAssetRequest = new DecreaseAssetRequest();
        decreaseAssetRequest.setAmount(amount);
        decreaseAssetRequest.setAssetType(awardType);
        decreaseAssetRequest.setUserId(request.getUserId());
        TcResult<Boolean> result = assetService.decreaseAsset(decreaseAssetRequest);
        // 用户资产不足
        if (!result.isSuccess()) {
            throw new LotteryException(LotteryResultEnum.ASSET_NOT_ENOUGH);
        }
        context.setDeductUserAsset(true);
    }

}



