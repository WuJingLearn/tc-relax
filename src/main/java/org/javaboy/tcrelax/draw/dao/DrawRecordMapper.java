package org.javaboy.tcrelax.draw.dao;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.tcrelax.draw.dataobject.DrawRecordDO;

import java.util.List;

/**
 * @author:majin.wj
 */
@Mapper
public interface DrawRecordMapper {


    int insert(DrawRecordDO recordDO);


    List<DrawRecordDO> selectByOutId(Long userId,String outId);

}
