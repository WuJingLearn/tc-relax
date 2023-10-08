package org.javaboy.relax.dal.mapper.draw;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.relax.dal.dataobject.draw.DrawRecordDO;

import java.util.List;

/**
 * @author:majin.wj
 */
@Mapper
public interface DrawRecordMapper {


    int insert(DrawRecordDO recordDO);


    List<DrawRecordDO> selectByOutId(Long userId, String outId);

}
