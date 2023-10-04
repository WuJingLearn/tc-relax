package org.javaboy.tcrelax.draw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.javaboy.tcrelax.dal.draw.BenefitGroupMapper;
import org.javaboy.tcrelax.draw.dataobject.BenefitGroupDO;
import org.javaboy.tcrelax.draw.dto.DrawActivityDTO;
import org.javaboy.tcrelax.draw.dto.DrawGroupDTO;
import org.javaboy.tcrelax.draw.service.IDrawGroupService;
import org.javaboy.tcrelax.draw.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
public class DrawGroupService implements IDrawGroupService {

    @Autowired
    private DrawActivityService drawActivityService;

    @Autowired(required = false)
    private BenefitGroupMapper benefitGroupMapper;

    public void createDrawGroup(DrawGroupDTO dto) {
        String activityId = dto.getActivityId();
        Assert.notNull(activityId, "");
        DrawActivityDTO drawActivity = drawActivityService.getDrawActivity(activityId);
        if (drawActivity == null) {
            throw new RuntimeException("活动id:" + activityId + "不存在");
        }
        BenefitGroupDO benefitGroupDO = convertDO(dto);
        benefitGroupMapper.insert(benefitGroupDO);
    }


    public List<DrawGroupDTO> queryDrawGroup(String activityId,String benefitGroupId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("activityId",activityId);
        queryParams.put("benefitGroupId",benefitGroupId);
        List<BenefitGroupDO> benefitGroupDO = benefitGroupMapper.selectByActivityId(queryParams);
        if (benefitGroupDO == null) {
            return Collections.emptyList();
        }
        return benefitGroupDO.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public BenefitGroupDO convertDO(DrawGroupDTO dto) {
        BenefitGroupDO benefitGroupDO = new BenefitGroupDO();
        benefitGroupDO.setActivityId(dto.getActivityId());
        benefitGroupDO.setBenefitGroupId(getBenefitGroupId());
        benefitGroupDO.setBenefitGroupName(dto.getBenefitGroupName());
        benefitGroupDO.setGmtCreate(new Date());
        benefitGroupDO.setGmtModified(new Date());
        JSONObject configInfo = new JSONObject();
        configInfo.put(Constants.RATIO, dto.getRatio());
        configInfo.put(Constants.PRIORITY, dto.getPriority());
        benefitGroupDO.setConfigInfo(configInfo.toString());
        return benefitGroupDO;
    }

    public DrawGroupDTO convertDTO(BenefitGroupDO groupDO) {
        DrawGroupDTO drawGroupDTO = new DrawGroupDTO();
        drawGroupDTO.setActivityId(groupDO.getActivityId());
        drawGroupDTO.setBenefitGroupId(groupDO.getBenefitGroupId());
        drawGroupDTO.setBenefitGroupName(groupDO.getBenefitGroupName());
        JSONObject jsonObject = JSON.parseObject(groupDO.getConfigInfo());
        drawGroupDTO.setRatio((Long) jsonObject.get(Constants.RATIO));
        drawGroupDTO.setPriority((Integer) jsonObject.get(Constants.PRIORITY));
        return drawGroupDTO;
    }

    private String getBenefitGroupId() {
        return "benefit_group_" + UUID.randomUUID().toString().replace("_", "");
    }


}
