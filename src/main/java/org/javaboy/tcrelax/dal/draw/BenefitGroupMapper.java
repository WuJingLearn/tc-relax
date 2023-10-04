package org.javaboy.tcrelax.dal.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.tcrelax.draw.dataobject.BenefitGroupDO;

import java.util.List;
import java.util.Map;

/**
 * @author:majin.wj
 */
@Mapper
public interface BenefitGroupMapper {

    void insert(BenefitGroupDO benefitGroupDO);

    List<BenefitGroupDO> selectByActivityId(Map<String,Object> queryParams);

}
