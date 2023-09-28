package org.javaboy.tcrelax.asset.service;

import org.javaboy.tcrelax.asset.service.request.DecreaseAssetRequest;
import org.javaboy.tcrelax.asset.service.request.IncreaseAssetRequest;
import org.javaboy.tcrelax.common.TcResult;

/**
 * @author:majin.wj 权益服务
 */
public interface IAssetService {


    public TcResult<Boolean> increaseAsset(IncreaseAssetRequest request);

    public TcResult<Boolean> decreaseAsset(DecreaseAssetRequest request);


}
