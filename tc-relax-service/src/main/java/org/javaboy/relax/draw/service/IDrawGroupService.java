package org.javaboy.relax.draw.service;


import org.javaboy.relax.draw.dto.DrawGroupDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface IDrawGroupService {


    void createDrawGroup(DrawGroupDTO dto);


    List<DrawGroupDTO> queryDrawGroup(String activityId, String benefitGroupId);

}
