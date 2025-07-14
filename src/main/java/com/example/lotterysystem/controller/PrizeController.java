package com.example.lotterysystem.controller;

import com.example.lotterysystem.common.pojo.CommonResult;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.controller.param.CreatePrizeParam;
import com.example.lotterysystem.service.PictureService;
import com.example.lotterysystem.service.PrizeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@RestController
public class PrizeController {
    @Autowired
    private PictureService pictureService;;
    @Autowired
    private PrizeService prizeService;
    @RequestMapping("/pic/upload")
    public String uploadPic(MultipartFile file){
        return pictureService.savePicture(file);
    }

    /**
     * 创建奖品
     * @param param
     * @param picFile
     * @return
     */
    @RequestMapping("/prize/create")
    public CommonResult<Long> createPrize(@Valid @RequestPart("param") CreatePrizeParam param,
                                          @RequestPart("prizePic") MultipartFile picFile){
        System.out.println(param);
        log.info("createPrize CreatePrizeParam:{}", JacksonUtil.writeValueAsString(param));
        return CommonResult.success(prizeService.createPrize(param,picFile));
    }

}
