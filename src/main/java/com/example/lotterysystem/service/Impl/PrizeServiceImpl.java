    package com.example.lotterysystem.service.Impl;

    import com.example.lotterysystem.controller.param.CreatePrizeParam;
    import com.example.lotterysystem.controller.param.PageParam;
    import com.example.lotterysystem.dao.mapper.PrizeMapper;
    import com.example.lotterysystem.service.PictureService;
    import com.example.lotterysystem.service.PrizeService;
    import com.example.lotterysystem.service.dto.PageListDTO;
    import com.example.lotterysystem.dao.dataobject.PrizeDO;
    import com.example.lotterysystem.service.dto.PrizeDTO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.ArrayList;
    import java.util.List;

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

        @Override
        public PageListDTO<PrizeDTO> findPrizeList(PageParam param) {
            //总量
            int total=prizeMapper.count();
            //查询当前列表
            List<PrizeDTO> prizeDTOList=new ArrayList<>();
            List<PrizeDO> prizeDOList=prizeMapper.selectPrizeList(param.offset(),param.getPageSize());
            for (PrizeDO prizeDO:prizeDOList){
                PrizeDTO prizeDTO = new PrizeDTO();
                prizeDTO.setPrizeId(prizeDO.getId());
                prizeDTO.setName(prizeDO.getName());
                prizeDTO.setDescription(prizeDO.getDescription());
                prizeDTO.setImageUrl(prizeDO.getImageUrl());
                prizeDTO.setPrice(prizeDO.getPrice());
                prizeDTOList.add(prizeDTO);
            }
            return new PageListDTO<>(total,prizeDTOList);
        }
    }
