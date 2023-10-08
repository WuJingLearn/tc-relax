package org.javaboy.relax.asset.service.impl;


import org.javaboy.relax.asset.service.IAssetService;
import org.javaboy.relax.asset.service.request.DecreaseAssetRequest;
import org.javaboy.relax.asset.service.request.IncreaseAssetRequest;
import org.javaboy.relax.common.TcResult;
import org.springframework.stereotype.Service;

/**
 * @author:majin.wj
 */
@Service
public class AssetServiceImpl implements IAssetService {


    @Override
    public TcResult<Boolean> increaseAsset(IncreaseAssetRequest request) {
        return null;
    }

    @Override
    public TcResult<Boolean> decreaseAsset(DecreaseAssetRequest request) {
        return null;
    }
}
