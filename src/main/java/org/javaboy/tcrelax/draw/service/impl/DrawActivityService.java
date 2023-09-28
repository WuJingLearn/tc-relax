package org.javaboy.tcrelax.draw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.javaboy.tcrelax.draw.dao.BenefitActivityMapper;
import org.javaboy.tcrelax.draw.dataobject.BenefitActivityDO;
import org.javaboy.tcrelax.draw.dto.DrawActivityDTO;
import org.javaboy.tcrelax.draw.dto.DrawGroupDTO;
import org.javaboy.tcrelax.draw.dto.DrawItemDTO;
import org.javaboy.tcrelax.draw.entity.CostBenefitEntity;
import org.javaboy.tcrelax.draw.entity.DrawActivityConfigEntity;
import org.javaboy.tcrelax.draw.entity.DrawGroupConfigEntity;
import org.javaboy.tcrelax.draw.entity.DrawItemConfigEntity;
import org.javaboy.tcrelax.draw.service.IDrawActivityService;
import org.javaboy.tcrelax.draw.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
@Slf4j
public class DrawActivityService implements IDrawActivityService {

    @Autowired
    private BenefitActivityMapper activityMapper;

    @Autowired
    private DrawGroupService groupService;

    @Autowired
    private DrawItemService drawItemService;

    public void createDrawActivity(DrawActivityDTO dto) {
        Assert.notNull(dto.getActivityName(), "");
        Assert.notNull(dto.getStartTime(), "");
        Assert.notNull(dto.getEndTime(), "");
        Assert.notNull(dto.getDrawModel(), "");
        Assert.notNull(dto.getCostBenefit(), "");
        BenefitActivityDO activityDO = covertDO(dto);
        activityMapper.insert(activityDO);
    }

    public DrawActivityConfigEntity getDrawActivityConfigEntity(String activityId) {
        BenefitActivityDO activityDO = activityMapper.selectByActivityId(activityId);
        if (activityDO == null) {
            log.info("发布活动失败,未找到活动,activityId:{}", activityId);
            return null;
        }
        // 活动配置
        DrawActivityConfigEntity drawActivityEntity = new DrawActivityConfigEntity();
        drawActivityEntity.setActivityId(activityDO.getActivityId());
        drawActivityEntity.setActivityName(activityDO.getActivityName());
        drawActivityEntity.setStartTime(activityDO.getStartTime());
        drawActivityEntity.setEndTime(activityDO.getEndTime());
        String configInfo = activityDO.getConfigInfo();
        JSONObject jsonObject = JSON.parseObject(configInfo);
        drawActivityEntity.setDrawMode((String) jsonObject.get(Constants.DRAW_MODE));
        drawActivityEntity.setCostBenefit((CostBenefitEntity) jsonObject.get(Constants.COST_CONFIG));

        List<DrawGroupDTO> drawGroups = groupService.queryDrawGroup(activityId, null);
        List<DrawGroupConfigEntity> groupConfigEntities = new ArrayList<>();
        for (DrawGroupDTO drawGroup : drawGroups) {
            DrawGroupConfigEntity groupEntity = new DrawGroupConfigEntity();
            groupEntity.setActivityId(drawActivityEntity.getActivityId());
            groupEntity.setBenefitGroupId(drawGroup.getBenefitGroupId());
            groupEntity.setBenefitGroupName(drawGroup.getBenefitGroupName());
            groupEntity.setPriority(drawGroup.getPriority());
            groupEntity.setRatio(drawGroup.getRatio());
            // 抽奖项配置
            List<DrawItemDTO> drawItemDTOS = drawItemService.queryDrawItem(drawGroup.getActivityId(), drawGroup.getBenefitGroupId());
            List<DrawItemConfigEntity> drawItemEntities = drawItemDTOS.stream().map(item -> {
                DrawItemConfigEntity itemEntity = new DrawItemConfigEntity();
                itemEntity.setBenefitId(item.getBenefitId());
                itemEntity.setBenefitName(item.getBenefitName());
                itemEntity.setAwardType(item.getAwardType());
                itemEntity.setAwardName(item.getAwardName());
                itemEntity.setAmount(item.getAmount());
                itemEntity.setUseInventory(item.isUseInventory());
                itemEntity.setInventoryConfig(item.getInventory());
                itemEntity.setRatio(item.getRatio());
                itemEntity.setOrder(item.getAmount());
                return itemEntity;
            }).collect(Collectors.toList());
            groupEntity.setDrawItems(drawItemEntities);
            // 抽奖组配置
            groupConfigEntities.add(groupEntity);
        }
        drawActivityEntity.setDrawGroupConfigs(groupConfigEntities);
        return drawActivityEntity;
    }

    public DrawActivityDTO getDrawActivity(String activityId) {

        BenefitActivityDO activityDO = activityMapper.selectByActivityId(activityId);
        if (activityDO == null) {
            return null;
        }
        return convertDTO(activityDO);
    }

    private BenefitActivityDO covertDO(DrawActivityDTO dto) {
        BenefitActivityDO activityDO = new BenefitActivityDO();
        activityDO.setActivityId(genActivityId());
        activityDO.setActivityName(dto.getActivityName());
        activityDO.setStartTime(new Date(dto.getStartTime()));
        activityDO.setEndTime(new Date(dto.getEndTime()));
        //抽奖
        activityDO.setType(1);
        activityDO.setGmtModified(new Date());
        activityDO.setGmtCreate(new Date());
        JSONObject configInfo = new JSONObject();
        configInfo.put(Constants.DRAW_MODE, dto.getDrawModel());
        configInfo.put(Constants.COST_CONFIG, dto.getCostBenefit());
        configInfo.put(Constants.SCHEDULER_ID, dto.getSchedulerId());
        activityDO.setConfigInfo(configInfo.toJSONString());
        return activityDO;
    }

    public DrawActivityDTO convertDTO(BenefitActivityDO activityDO) {
        return null;
    }


    private String genActivityId() {
        return "activity_" + UUID.randomUUID().toString().replace("_", "");
    }


}
