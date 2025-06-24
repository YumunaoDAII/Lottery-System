package com.example.lotterysystem.service.Impl;

import com.example.lotterysystem.controller.param.UserRegisterParam;
import com.example.lotterysystem.service.UserService;
import com.example.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserRegisterDTO register(UserRegisterParam param) {
        //校验注册信息
        //加密私密数据
        //保存数据
        //构造返回
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUserId(12L);
        return userRegisterDTO;
    }
}
