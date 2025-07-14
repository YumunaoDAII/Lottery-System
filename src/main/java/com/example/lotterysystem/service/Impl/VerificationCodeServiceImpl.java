package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.*;
import com.example.lotterysystem.dao.mapper.UserMapper;
import com.example.lotterysystem.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    //为了区分业务，给key定义前缀
    //VerificationCode_13111111111:123、User_13111111111:userInfo
    private static final String VERIFICATION_CODE_PREFIX="VERIFICATION_CODE_";
    private static final Long VERIFICATION_CODE_TIMEOUT=180L;
    private static final String VERIFICATION_CODE_TEMPLATE="";
    @Autowired
    private SMSUtil smsUtil;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void sendVerificationCode(String phoneNumber) {
        //校验手机号
        if (!RegexUtil.checkMobile(phoneNumber)){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_ERROR);
        }
        //生成随机验证码
        String code = CaptchaUtil.getCaptchaUtil(4);
        //{"code":"xxxx"}
        Map<String,String> map=new HashMap<>();
        map.put("code",code);


        //发送验证码
//        smsUtil.sendMessage();
        //缓存验证码
        redisUtil.set(VERIFICATION_CODE_PREFIX+phoneNumber,code,VERIFICATION_CODE_TIMEOUT);
    }

    @Override
    public String getVerificationCode(String phoneNumber) {
        //校验手机号
        if (!RegexUtil.checkMobile(phoneNumber)){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_ERROR);
        }
        return redisUtil.get(VERIFICATION_CODE_PREFIX+phoneNumber);
    }

    @Override
    public void sendVerificationEmailCode(String email) {
        System.out.println("[Debug sendVerificationEmailCode]  email:"+email);
        //校验邮箱号
        if (!RegexUtil.checkMail(email)){
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_ERROR);
        }
        if (userMapper.countByMail(email)<1){
            throw new ServiceException(ServiceErrorCodeConstants.USER_INFO_IS_EMPTY);
        }
        //生成随机验证码
        String code = CaptchaUtil.getCaptchaUtil(4);
        //{"code":"xxxx"}
        Map<String,String> map=new HashMap<>();
        map.put("code",code);


        //发送验证码
//        smsUtil.sendMessage();
        mailUtil.sendMailCode(email,code);
        //缓存验证码
        redisUtil.set(VERIFICATION_CODE_PREFIX+email,code,VERIFICATION_CODE_TIMEOUT);
    }

    @Override
    public String getVerificationEmailCode(String email) {
        //校验邮箱
        if (!RegexUtil.checkMail(email)){
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_ERROR);
        }
        //
        return redisUtil.get(VERIFICATION_CODE_PREFIX+email);
    }
}
