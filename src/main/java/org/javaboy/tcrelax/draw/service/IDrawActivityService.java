package org.javaboy.tcrelax.draw.service;

import org.javaboy.tcrelax.draw.dto.DrawActivityDTO;
import org.javaboy.tcrelax.draw.entity.DrawActivityConfigEntity;

/**
 * @author:majin.wj
 */
public interface IDrawActivityService {

    void createDrawActivity(DrawActivityDTO dto);

    DrawActivityConfigEntity getDrawActivityConfigEntity(String activityId);


}
