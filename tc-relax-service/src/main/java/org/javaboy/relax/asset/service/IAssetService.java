package org.javaboy.relax.asset.service;


import org.javaboy.relax.asset.service.request.DecreaseAssetRequest;
import org.javaboy.relax.asset.service.request.IncreaseAssetRequest;
import org.javaboy.relax.common.TcResult;

/**
 * @author:majin.wj 权益服务
 */
public interface IAssetService {


    public TcResult<Boolean> increaseAsset(IncreaseAssetRequest request);

    public TcResult<Boolean> decreaseAsset(DecreaseAssetRequest request);


}
