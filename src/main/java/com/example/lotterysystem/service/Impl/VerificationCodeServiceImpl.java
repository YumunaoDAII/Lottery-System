package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.CaptchaUtil;
import com.example.lotterysystem.common.utils.RegexUtil;
import com.example.lotterysystem.service.VerificationCodeService;

public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Override
    public void sendVerificationCode(String phoneNumber) {
        //校验手机号
        if (!RegexUtil.checkMobile(phoneNumber)){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_ERROR);
        }
        //生成随机验证码
        String code = CaptchaUtil.getCaptchaUtil(4);
        //发送验证码

    }

    @Override
    public String getVerificationCode(String phoneNumber) {
        return null;
    }
}
