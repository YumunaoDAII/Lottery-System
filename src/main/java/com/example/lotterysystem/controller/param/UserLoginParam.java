package com.example.lotterysystem.controller.param;

import com.example.lotterysystem.service.enums.UserIdentityEnum;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserLoginParam implements Serializable {
    /**
     * 强制身份登录，不填不限制
     * @see UserIdentityEnum#name()
     */
    private String mandatoryIdentity;
}
