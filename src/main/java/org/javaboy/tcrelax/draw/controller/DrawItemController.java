package org.javaboy.tcrelax.draw.controller;

import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.draw.dto.DrawItemDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:majin.wj
 */
@RequestMapping("/drwaItem")
@RestController
public class DrawItemController {


    @PostMapping
    public TcResult<Void> createDrawItem(DrawItemDTO dto){
        return null;
    }



}
