package org.javaboy.relax.dal.mapper.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.relax.dal.dataobject.draw.BenefitGroupDO;

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
