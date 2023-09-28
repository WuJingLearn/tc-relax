package org.javaboy.tcrelax.draw.controller;

import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.draw.dto.DrawGroupDTO;
import org.javaboy.tcrelax.draw.service.impl.DrawGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:majin.wj
 */

@RequestMapping("/drawGroup")
@RestController
public class DrawGroupController {

    @Autowired
    private DrawGroupService drawGroupService;

    @PostMapping
    public TcResult<Void> createDrawGroup(DrawGroupDTO dto) {
        try {
            drawGroupService.createDrawGroup(dto);
            return TcResult.success(null);
        } catch (Exception e) {
            return TcResult.fail("createDrawGroup fail " + e.getMessage());
        }
    }


}
