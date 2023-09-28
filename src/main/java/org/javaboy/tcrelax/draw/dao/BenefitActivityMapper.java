package org.javaboy.tcrelax.draw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.tcrelax.draw.dataobject.BenefitActivityDO;

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
