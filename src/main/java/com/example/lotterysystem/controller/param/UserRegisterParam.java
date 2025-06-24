package com.example.lotterysystem.controller.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserRegisterParam implements Serializable {
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "邮箱不能为空")
    private String mail;
    @NotBlank(message = "电话不能为空")
    private String phoneNumber;
    private String password;
    /**
     * 身份信息
     */
    @NotBlank(message = "身份信息不能为空")
    private String identity;
}
