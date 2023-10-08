package org.javaboy.relax.draw.service;


import org.javaboy.relax.draw.dto.DrawActivityDTO;
import org.javaboy.relax.draw.entity.DrawActivityConfigEntity;

/**
 * @author:majin.wj
 */
public interface IDrawActivityService {

    void createDrawActivity(DrawActivityDTO dto);

    DrawActivityConfigEntity getDrawActivityConfigEntity(String activityId);


}
