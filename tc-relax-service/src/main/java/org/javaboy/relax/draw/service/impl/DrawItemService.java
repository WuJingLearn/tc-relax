package org.javaboy.relax.draw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.javaboy.relax.dal.dataobject.draw.BenefitItemDO;
import org.javaboy.relax.dal.mapper.draw.BenefitItemMapper;
import org.javaboy.relax.draw.dto.DrawGroupDTO;
import org.javaboy.relax.draw.dto.DrawItemDTO;
import org.javaboy.relax.draw.entity.InventoryConfigEntity;
import org.javaboy.relax.draw.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author:majin.wj
 */
@Service
public class DrawItemService {


    @Autowired(required = false)
    private BenefitItemMapper benefitItemMapper;

    @Autowired
    private DrawGroupService groupService;

    public void createBenefitItem(DrawItemDTO dto) {
        String activityId = dto.getActivityId();
        String benefitGroupId = dto.getBenefitGroupId();

        List<DrawGroupDTO> drawGroup = groupService.queryDrawGroup(activityId, benefitGroupId);
        if (drawGroup == null || drawGroup.size() == 0) {
            throw new RuntimeException("抽奖组不存在");
        }
        benefitItemMapper.insert(convertDO(dto));
    }

    public List<DrawItemDTO> queryDrawItem(String activityId, String benefitGroupId) {
        List<BenefitItemDO> benefitItems = benefitItemMapper.select(activityId, benefitGroupId);
        if (benefitItems == null) {
            return Collections.emptyList();
        }
        return benefitItems.stream().map(this::convertDTO).collect(Collectors.toList());
    }

    public BenefitItemDO convertDO(DrawItemDTO dto) {
        BenefitItemDO benefitItemDO = new BenefitItemDO();
        benefitItemDO.setActivityId(dto.getActivityId());
        benefitItemDO.setBenefitGroupId(dto.getBenefitGroupId());
        benefitItemDO.setBenefitId(genBenefitId());

        JSONObject configInfo = new JSONObject();
        // 抽奖比率
        configInfo.put(Constants.RATIO, dto.getRatio());
        configInfo.put(Constants.AWARD_TYPE, dto.getAwardType());
        configInfo.put(Constants.AWARD_NAME, dto.getAwardName());
        configInfo.put(Constants.AWARD_AMOUNT, dto.getAmount());
        configInfo.put("useInventory", dto.isUseInventory());
        if (dto.isUseInventory()) {
            configInfo.put("inventoryInfo", dto.getInventory());
        }
        configInfo.put("awardType", dto.getAwardType());
        configInfo.put("awardName", dto.getAwardName());
        // 一次发放的数量
        configInfo.put("awardAmount", dto.getAmount());
        benefitItemDO.setConfigInfo(configInfo.toString());
        return benefitItemDO;
    }

    public DrawItemDTO convertDTO(BenefitItemDO itemDO) {
        DrawItemDTO drawItemDTO = new DrawItemDTO();
        drawItemDTO.setActivityId(itemDO.getActivityId());
        drawItemDTO.setBenefitGroupId(itemDO.getBenefitGroupId());
        drawItemDTO.setBenefitName(itemDO.getBenefitName());
        drawItemDTO.setBenefitId(itemDO.getBenefitId());
        JSONObject jsonObject = JSON.parseObject(itemDO.getConfigInfo());
        Long ratio = (Long) jsonObject.get(Constants.RATIO);
        String awardName = (String) jsonObject.get(Constants.AWARD_NAME);
        String awardType = (String) jsonObject.get(Constants.AWARD_TYPE);
        Integer awardAmount = (Integer) jsonObject.get(Constants.AWARD_AMOUNT);
        Boolean useInventory = (Boolean) jsonObject.get(Constants.USE_INVENTORY);
        if (useInventory) {
            InventoryConfigEntity inventoryInfo = (InventoryConfigEntity) jsonObject.get("inventoryInfo");
            drawItemDTO.setInventory(inventoryInfo);
        }
        drawItemDTO.setRatio(ratio);
        drawItemDTO.setAmount(awardAmount);
        drawItemDTO.setAwardType(awardType);
        drawItemDTO.setAwardName(awardName);
        drawItemDTO.setUseInventory(useInventory);
        return drawItemDTO;
    }

    private String genBenefitId() {
        return "benefit_" + UUID.randomUUID().toString().replace("_", "");
    }


}
