package com.example.lotterysystem.controller;

import com.example.lotterysystem.common.pojo.CommonResult;
import com.example.lotterysystem.controller.param.DrawPrizeParam;
import com.example.lotterysystem.service.DrawPrizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class DrawPrizeController {
    @Autowired
    private DrawPrizeService drawPrizeService;

    @RequestMapping("/draw-prize")
    public CommonResult<Boolean> drawPrize(@RequestBody @Validated DrawPrizeParam param){
        log.info("drawPrize DrawPrizeParam:{}",param);
        //service
        drawPrizeService.drawPrize(param);

        return CommonResult.success(true);

    }
}
