package com.example.lotterysystem.service;


import com.example.lotterysystem.controller.param.UserLoginParam;
import com.example.lotterysystem.controller.param.UserPasswordLoginParam;
import com.example.lotterysystem.controller.param.UserRegisterParam;
import com.example.lotterysystem.service.dto.UserLoginDTO;
import com.example.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    /**
     * 用户注册
     * @param param
     * @return
     */
    UserRegisterDTO register(UserRegisterParam param);

    /**
     * 用户登录
     * 1、密码
     * 2、验证码
     * @param param
     * @return
     */

    UserLoginDTO login(UserLoginParam param);
}
