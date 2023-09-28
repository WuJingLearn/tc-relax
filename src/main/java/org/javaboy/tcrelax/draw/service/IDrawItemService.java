package org.javaboy.tcrelax.draw.service;

import org.javaboy.tcrelax.draw.dto.DrawItemDTO;

import java.util.List;

/**
 * @author:majin.wj
 */
public interface IDrawItemService {

    void createBenefitItem(DrawItemDTO dto);

    List<DrawItemDTO> queryDrawItem(String activityId, String benefitGroupId);

}
