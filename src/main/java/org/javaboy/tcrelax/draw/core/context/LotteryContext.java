package org.javaboy.tcrelax.draw.core.context;

import lombok.Data;
import org.javaboy.tcrelax.draw.dataobject.DrawRecordDO;
import org.javaboy.tcrelax.draw.entity.DrawActivityConfigEntity;
import org.javaboy.tcrelax.draw.entity.DrawItemConfigEntity;

import java.util.List;

/**
 * @author:majin.wj
 */
@Data
public class LotteryContext {

    private String scene;

    private int drawTime;

    private List<DrawRecordDO> drawRecord;

    private DrawActivityConfigEntity drawActivityConfigEntity;

    /**
     * 抽中的奖励
     */
    private DrawItemConfigEntity drwaItemConfigEntity;

    /**
     * 是否消耗了用户权益
     */
    private boolean deductUserAsset;

    /**
     * 是否消耗了库存;
     */
    private boolean deductInventory;




    public boolean isRepeated() {
        return drawRecord != null && drawRecord.size() > 0;
    }


}
