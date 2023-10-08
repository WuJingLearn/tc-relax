package org.javaboy.relax.dal.mapper.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.relax.dal.dataobject.draw.BenefitItemDO;

import java.util.List;

/**
 * @author:majin.wj
 */
@Mapper
public interface BenefitItemMapper {


    int insert(BenefitItemDO benefitItemDO);

    List<BenefitItemDO> select(String activityId,String benefitGroupId);

}
