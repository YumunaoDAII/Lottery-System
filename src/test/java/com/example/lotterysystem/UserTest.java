package com.example.lotterysystem;

import com.example.lotterysystem.service.UserService;
import com.example.lotterysystem.service.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    void UserList(){
        List<UserDTO> userDTOList = userService.findUserInfo(null);
        for (UserDTO userDTO : userDTOList) {
            System.out.print(userDTO.getUserId()+"  ");
            System.out.print(userDTO.getUserName()+"  ");
            System.out.print(userDTO.getIdentity().name()+"  ");
            System.out.println("");
        }
    }
}
