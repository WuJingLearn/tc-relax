package org.javaboy.relax.contraller.draw;

import org.javaboy.relax.common.TcResult;
import org.javaboy.relax.draw.dto.DrawItemDTO;
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
