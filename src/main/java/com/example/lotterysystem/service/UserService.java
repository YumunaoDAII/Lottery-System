package com.example.lotterysystem.service;


import com.example.lotterysystem.controller.param.UserRegisterParam;
import com.example.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

public interface UserService {
    UserRegisterDTO register(UserRegisterParam param);
}
