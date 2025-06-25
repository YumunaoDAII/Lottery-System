package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.utils.RegexUtil;
import com.example.lotterysystem.controller.param.UserRegisterParam;
import com.example.lotterysystem.dao.dataobject.Encrypt;
import com.example.lotterysystem.dao.mapper.UserMapper;
import com.example.lotterysystem.service.UserService;
import com.example.lotterysystem.service.dto.UserRegisterDTO;
import com.example.lotterysystem.service.enums.UserIdentityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        //校验注册信息
        checkRegisterInfo(param);
        //加密私密数据
        //保存数据
        //构造返回
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(12L);
        return userRegisterDTO;
    }

    private void checkRegisterInfo(UserRegisterParam param) {
        if (null==param){
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_IS_EMPTY);
        }
        //校验邮箱格式
        if (!RegexUtil.checkMail(param.getMail())){
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_ERROR);
        }
        //校验手机号
        if (!RegexUtil.checkMobile(param.getPhoneNumber())){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_ERROR);
        }
        //校验身份信息
        if (null== UserIdentityEnum.forName(param.getIdentity())){
            throw new ServiceException(ServiceErrorCodeConstants.IDENTITY_ERROR);
        }
        //校验管理员密码必填
        if (param.getIdentity().equalsIgnoreCase(UserIdentityEnum.ADMIN.name())
            && !StringUtils.hasText(param.getPassword())){
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_IS_EMPTY);
        }
        //密码校验
        if (StringUtils.hasText(param.getPassword())&&!RegexUtil.checkPassword(param.getPassword())){
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_ERROR);
        }
        //校验邮箱是否被使用
        if (checkMailUsed(param.getMail())){
            throw new ServiceException(ServiceErrorCodeConstants.MAIL_USED);
        }
        //校验手机号是否被使用
        if (checkPhoneNumberUsed(param.getPhoneNumber())){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_USED);
        }
    }

    private boolean checkPhoneNumberUsed(String phoneNumber) {
        int count=userMapper.countByPhone(new Encrypt(phoneNumber));
        return count>0;
    }

    private boolean checkMailUsed(String mail) {
        int count= userMapper.countByMail(mail);
        return count>0;
    }
}
