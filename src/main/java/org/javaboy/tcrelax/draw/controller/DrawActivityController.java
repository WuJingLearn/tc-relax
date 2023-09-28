package org.javaboy.tcrelax.draw.controller;

import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.draw.core.config.DrawActivityConfigManager;
import org.javaboy.tcrelax.draw.dto.DrawActivityDTO;
import org.javaboy.tcrelax.draw.service.impl.DrawActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author:majin.wj
 */
@RequestMapping("/drawActivity")
@RestController
public class DrawActivityController {

    @Autowired
    private DrawActivityService drawActivityService;

    @Autowired
    private DrawActivityConfigManager configManager;

    /**
     * 创建抽奖活动
     * @param drawActivityDTO
     * @return
     */
    @PostMapping
    public TcResult<Void> createDrawActivity(DrawActivityDTO drawActivityDTO) {
        try {
            drawActivityService.createDrawActivity(drawActivityDTO);
            return TcResult.success(null);
        } catch (Exception e) {
            return TcResult.fail("createDrawActivity fail " + e.getMessage());
        }
    }


    /**
     * 发布抽奖活动;随时可以发布，发布完，配置就更换
     *
     * @param activityId
     * @return
     */
    @PostMapping("/publish")
    public TcResult<Void> publishActivity(String activityId) {
        try {
            configManager.publishDrawActivity(activityId);
            return TcResult.success(null);
        } catch (Exception e) {
            return TcResult.fail("publishActivity fail " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("ab", "a", "c"));

        list.removeIf(item -> !item.contains("a"));
        System.out.println(
                list
        );
    }

}
