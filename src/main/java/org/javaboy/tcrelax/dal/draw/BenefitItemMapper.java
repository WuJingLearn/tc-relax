package org.javaboy.tcrelax.dal.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.tcrelax.draw.dataobject.BenefitItemDO;

import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj
 */
@Mapper
public interface BenefitItemMapper {


    int insert(BenefitItemDO benefitItemDO);

    List<BenefitItemDO> select(String activityId,String benefitGroupId);

}
