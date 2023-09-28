package org.javaboy.tcrelax.draw.service;

import org.javaboy.tcrelax.draw.dto.DrawGroupDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface IDrawGroupService {


    void createDrawGroup(DrawGroupDTO dto);


    List<DrawGroupDTO> queryDrawGroup(String activityId, String benefitGroupId);

}
