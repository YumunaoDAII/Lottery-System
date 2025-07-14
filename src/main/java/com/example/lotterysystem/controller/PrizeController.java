package com.example.lotterysystem.controller;

import com.example.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.example.lotterysystem.common.exception.ControllerException;
import com.example.lotterysystem.common.pojo.CommonResult;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.controller.param.CreatePrizeParam;
import com.example.lotterysystem.controller.param.PageParam;
import com.example.lotterysystem.controller.result.FindPrizeListResult;
import com.example.lotterysystem.service.PictureService;
import com.example.lotterysystem.service.PrizeService;
import com.example.lotterysystem.service.dto.PageListDTO;
import com.example.lotterysystem.service.dto.PrizeDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

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
    @RequestMapping("/prize/find-list")
    public CommonResult<FindPrizeListResult> findPrizeList(PageParam param){
        log.info("findPrizeList PageParam:{}",JacksonUtil.writeValueAsString(param));
        PageListDTO<PrizeDTO> pageListDTO=prizeService.findPrizeList(param);
        return CommonResult.success(convertToFindPrizeListResult(pageListDTO));
    }

    private FindPrizeListResult convertToFindPrizeListResult(PageListDTO<PrizeDTO> pageListDTO) {
        if (null==pageListDTO){
            throw new ControllerException(ControllerErrorCodeConstants.FIND_PRIZE_LIST_ERR);
        }
        FindPrizeListResult result=new FindPrizeListResult();
        result.setTotal(pageListDTO.getTotal());
        result.setRecords(
                pageListDTO.getRecords().stream().map(prizeDTO -> {
                    FindPrizeListResult.PrizeInfo prizeInfo = new FindPrizeListResult.PrizeInfo();
                    prizeInfo.setPrizeId(prizeDTO.getPrizeId());
                    prizeInfo.setPrizeName(prizeDTO.getName());
                    prizeInfo.setDescription(prizeDTO.getDescription());
                    prizeInfo.setPrice(prizeDTO.getPrice());
                    prizeInfo.setImageUrl(prizeDTO.getImageUrl());
                    return prizeInfo;
                }).collect(Collectors.toList())
        );
        return result;
    }
}
