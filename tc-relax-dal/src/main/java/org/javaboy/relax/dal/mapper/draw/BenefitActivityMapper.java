package org.javaboy.relax.dal.mapper.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.relax.dal.dataobject.draw.BenefitActivityDO;

/**
 * @author:majin.wj
 *
 * 活动,后期兑换抽奖可以共用一个数据库
 */
@Mapper
public interface BenefitActivityMapper {
    void insert(BenefitActivityDO activityDO);

    BenefitActivityDO selectByActivityId(String activityId);

}
