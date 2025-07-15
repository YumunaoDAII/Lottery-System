package com.example.lotterysystem.controller.param;

import lombok.Data;

import java.io.Serializable;
@Data
public class CreateUserByActivityParam implements Serializable {
    private Long userId;
    private String userName;

}
