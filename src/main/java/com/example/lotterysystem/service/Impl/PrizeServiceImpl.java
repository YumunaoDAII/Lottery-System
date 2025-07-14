    package com.example.lotterysystem.service.Impl;

    import com.example.lotterysystem.controller.param.CreatePrizeParam;
    import com.example.lotterysystem.dao.mapper.PrizeMapper;
    import com.example.lotterysystem.service.PictureService;
    import com.example.lotterysystem.service.PrizeService;
    import com.example.lotterysystem.service.dto.PrizeDO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;
    @Service
    public class PrizeServiceImpl implements PrizeService {
        @Autowired
        private PictureService pictureService;
        @Autowired
        private PrizeMapper prizeMapper;
        @Override
        public Long createPrize(CreatePrizeParam param, MultipartFile picFile) {
            System.out.println("param为：" + param);
            //上传图片
            String filename = pictureService.savePicture(picFile);
            //存数据库
            PrizeDO prizeDO = new PrizeDO();
            prizeDO.setName(param.getPrizeName());
            prizeDO.setDescription(param.getDescription());
            prizeDO.setImageUrl(filename);
            prizeDO.setPrice(param.getPrice());
            prizeMapper.insert(prizeDO);
            return prizeDO.getId();
        }
    }
