package org.javaboy.tcrelax.exchange.inner.cost;

/**
 * @author:majin.wj
 */
public interface CostBenefitExtension {

    /**
     * 查询数额
     * @return
     */
    Integer queryAmount();

    boolean deductUserAsset(Integer amount,String uid);


    /**
     * 增加数额
     */
    void increaseAmount(Integer amount,String uid);


}
