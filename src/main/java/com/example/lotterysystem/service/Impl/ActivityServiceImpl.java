package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.common.utils.RedisUtil;
import com.example.lotterysystem.controller.param.CreateActivityParam;
import com.example.lotterysystem.controller.param.CreatePrizeByActivityParam;
import com.example.lotterysystem.controller.param.CreateUserByActivityParam;
import com.example.lotterysystem.dao.dataobject.ActivityDO;
import com.example.lotterysystem.dao.dataobject.ActivityPrizeDO;
import com.example.lotterysystem.dao.dataobject.ActivityUserDO;
import com.example.lotterysystem.dao.dataobject.PrizeDO;
import com.example.lotterysystem.dao.mapper.*;
import com.example.lotterysystem.service.ActivityService;
import com.example.lotterysystem.service.dto.ActivityDetailDTO;
import com.example.lotterysystem.service.dto.CreateActivityDTO;
import com.example.lotterysystem.service.enums.ActivityPrizeStatusEnum;
import com.example.lotterysystem.service.enums.ActivityPrizeTiersEnum;
import com.example.lotterysystem.service.enums.ActivityStatusEnum;
import com.example.lotterysystem.service.enums.ActivityUserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {
    /**
     * 活动缓存前缀，为了区分业务
     */
    private final String ACTIVITY_PREFIX="ACTIVITY_";
    /**
     * 活动缓存过期时间
     */
    private final Long ACTIVITY_TIMEOUT=60*60*24*3L;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PrizeMapper prizeMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityPrizeMapper activityPrizeMapper;
    @Autowired
    private ActivityUserMapper activityUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    @Transactional(rollbackFor = Exception.class) //涉及多表，保证事物失败回滚
    public CreateActivityDTO createActivity(CreateActivityParam param) {
        //校验活动信息是否正确
        checkActivityIfo(param);
        //保存活动信息
        ActivityDO activityDO = new ActivityDO();
        activityDO.setActivityName(param.getActivityName());
        activityDO.setDescription(param.getDescription());
        activityDO.setStatus(ActivityStatusEnum.RUNNING.name());
        activityMapper.insert(activityDO);
        //保存活动管理奖品信息
        List<CreatePrizeByActivityParam> prizeParams = param.getActivityPrizeList();
        List<ActivityPrizeDO> activityPrizeDOList=prizeParams.stream()
                .map(prizeParam->{
                    ActivityPrizeDO activityPrizeDO = new ActivityPrizeDO();
                    activityPrizeDO.setActivityId(activityDO.getId());
                    activityPrizeDO.setPrizeId(prizeParam.getPrizeId());
                    activityPrizeDO.setPrizeAmount(prizeParam.getPrizeAmount());
                    activityPrizeDO.setPrizeTiers(prizeParam.getPrizeTiers());
                    activityPrizeDO.setStatus(ActivityPrizeStatusEnum.INIT.name());
                    return activityPrizeDO;
                }).collect(Collectors.toList());
        activityPrizeMapper.batchInsert(activityPrizeDOList);
        //保存活动人员奖品信息
        List<CreateUserByActivityParam> userParams = param.getActivityUserList();
        List<ActivityUserDO> activityUserDOList=userParams.stream()
                .map(userParam->{
                    ActivityUserDO activityUserDO = new ActivityUserDO();
                    activityUserDO.setActivityId(activityDO.getId());
                    activityUserDO.setUserId(userParam.getUserId());
                    activityUserDO.setUserName(userParam.getUserName());
                    activityUserDO.setStatus(ActivityUserStatusEnum.INIT.name());
                    return activityUserDO;
                }).collect(Collectors.toList());
        activityUserMapper.batchInsert(activityUserDOList);
        //整合完整活动信息，存放至redis
        //activityId: ActivityDetailDTO:活动+奖品+人员

        //先获取奖品基本属性列表
        List<Long> prizeIds = param.getActivityPrizeList().stream()
                .map(CreatePrizeByActivityParam::getPrizeId).distinct().collect(Collectors.toList());
        List<PrizeDO> prizeDOList=prizeMapper.batchSelectByIds(prizeIds);
        ActivityDetailDTO detailDTO =convertToActivityDetailDTO(activityDO,activityUserDOList,prizeDOList,activityPrizeDOList);
        cacheActivity(detailDTO);


        //构造返回
        CreateActivityDTO createActivityDTO = new CreateActivityDTO();
        createActivityDTO.setActivityId(activityDO.getId());
        return createActivityDTO;

    }

    private void cacheActivity(ActivityDetailDTO detailDTO) {
        //key: ACTIVITY_activityId
        //value: ActivityDetailDTO(json)
        if (null==detailDTO || null==detailDTO.getActivityId()){
            log.warn("要缓存的活动信息不存在");
            return;
        }
        try {
            redisUtil.set(ACTIVITY_PREFIX+detailDTO.getActivityId(), JacksonUtil.writeValueAsString(detailDTO),ACTIVITY_TIMEOUT);
        }catch (Exception e){
            log.error("缓存活动异常，ActivityDetailDTO={}",JacksonUtil.writeValueAsString(detailDTO),e);
        }

    }

    /**
     * 根据活动id从缓存获取活动详细信息
     * @param activityId
     * @return
     */
    private ActivityDetailDTO grtActivityFromCache(Long activityId){
        if (null==activityId){
            log.warn("获取活动数据的activityId为空！");
            return null;
        }
        String str = redisUtil.get(ACTIVITY_PREFIX + activityId);
        if (!StringUtils.hasText(str)){
            log.warn("获取的缓存活动数据为空！key={}",ACTIVITY_PREFIX + activityId);
            return null;
        }
        try {
            return JacksonUtil.readValue(str, ActivityDetailDTO.class);
        }catch (Exception e){
            log.error("从缓存获取活动异常，activityId={}",ACTIVITY_PREFIX + activityId,e);
            return null;
        }

    }


    private ActivityDetailDTO convertToActivityDetailDTO(ActivityDO activityDO,
                                                         List<ActivityUserDO> activityUserDOList,
                                                         List<PrizeDO> prizeDOList,
                                                         List<ActivityPrizeDO> activityPrizeDOList) {
        ActivityDetailDTO detailDTO = new ActivityDetailDTO();
        detailDTO.setActivityId(activityDO.getId());
        detailDTO.setActivityName(activityDO.getActivityName());
        detailDTO.setDesc(activityDO.getDescription());
        detailDTO.setStatus(ActivityStatusEnum.forName(activityDO.getStatus()));

        // apDO: {prizeId，amount, status}, {prizeId，amount, status}
        // pDO: {prizeid, name....},{prizeid, name....},{prizeid, name....}
        List<ActivityDetailDTO.PrizeDTO> prizeDTOList = activityPrizeDOList
                .stream()
                .map(apDO -> {
                    ActivityDetailDTO.PrizeDTO prizeDTO = new ActivityDetailDTO.PrizeDTO();
                    prizeDTO.setPrizeId(apDO.getPrizeId());
                    Optional<PrizeDO> optionalPrizeDO = prizeDOList.stream()
                            .filter(prizeDO -> prizeDO.getId().equals(apDO.getPrizeId()))
                            .findFirst();
                    // 如果PrizeDO为空，不执行当前方法，不为空才执行
                    optionalPrizeDO.ifPresent(prizeDO -> {
                        prizeDTO.setName(prizeDO.getName());
                        prizeDTO.setImageUrl(prizeDO.getImageUrl());
                        prizeDTO.setPrice(prizeDO.getPrice());
                        prizeDTO.setDescription(prizeDO.getDescription());
                    });
                    prizeDTO.setTiers(ActivityPrizeTiersEnum.forName(apDO.getPrizeTiers()));
                    prizeDTO.setPrizeAmount(apDO.getPrizeAmount());
                    prizeDTO.setStatus(ActivityPrizeStatusEnum.forName(apDO.getStatus()));
                    return prizeDTO;
                }).collect(Collectors.toList());
        detailDTO.setPrizeDTOList(prizeDTOList);

        List<ActivityDetailDTO.UserDTO> userDTOList = activityUserDOList.stream()
                .map(auDO -> {
                    ActivityDetailDTO.UserDTO userDTO = new ActivityDetailDTO.UserDTO();
                    userDTO.setUserId(auDO.getUserId());
                    userDTO.setUserName(auDO.getUserName());
                    userDTO.setStatus(ActivityUserStatusEnum.forName(auDO.getStatus()));
                    return userDTO;
                }).collect(Collectors.toList());
        detailDTO.setUserDTOList(userDTOList);
        return detailDTO;
    }


    /**
     * 校验活动信息
     * @param param
     */
    private void checkActivityIfo(CreateActivityParam param) {
        if (null==param){
            throw new ServiceException(ServiceErrorCodeConstants.CREATE_ACTIVITY_INFO_IS_EMPTY);
        }
        //校验人员id在人员表中存在
        List<Long> userIds=param.getActivityUserList().stream()
                .map(CreateUserByActivityParam::getUserId)
                .distinct() //去重
                .collect(Collectors.toList());
        List<Long> existUserIds = userMapper.selectExistsByIds(userIds);
        userIds.forEach(id->{
            if (!existUserIds.contains(id)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_USER_ERR);
            }
        });
        //校验奖品id在奖品表中存在
        List<Long> prizeIds=param.getActivityPrizeList().stream()
                .map(CreatePrizeByActivityParam::getPrizeId).distinct().collect(Collectors.toList());
        List<Long> existPrizeIds=prizeMapper.selectExiseByIds(prizeIds);
        prizeIds.forEach(id->{
            if (!existPrizeIds.contains(id)){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_ERR);
            }
        });
        ///人员数量大于等于奖品数量
        Integer userAmount=param.getActivityUserList().size();
        long prizeAmount=param.getActivityPrizeList().stream()
                .mapToLong(CreatePrizeByActivityParam::getPrizeAmount).sum();
        if (userAmount<prizeAmount){
            throw new ServiceException(ServiceErrorCodeConstants.USER_PRIZE_AMOUNT_ERROR);
        }
        //校验活动奖品等级有效性
        param.getActivityPrizeList().forEach(pize->{
            if (null== ActivityPrizeTiersEnum.forName(pize.getPrizeTiers())){
                throw new ServiceException(ServiceErrorCodeConstants.ACTIVITY_PRIZE_TIERS_ERROR);
            }
        });
    }


}
