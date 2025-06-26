package com.example.lotterysystem.controller.result;

import lombok.Data;

@Data
public class UserLoginResult {
    //JWT令牌
    private String token;
    //登录人员身份
    private String identity;

}
