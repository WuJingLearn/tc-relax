package org.javaboy.tcrelax.asset.service.request;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class AssetRequest {
    /**
     * 资产类型
     */
    private String assetType;

    private Long userId;

}
