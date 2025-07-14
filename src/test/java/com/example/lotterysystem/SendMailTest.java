package com.example.lotterysystem;

import com.example.lotterysystem.common.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SendMailTest {
    @Autowired
    private MailUtil mailUtil;
    @Test
    void setMailUtil(){
        String mail="13246qq.com";
        String code="1234";
        mailUtil.sendMailCode(mail,code);
    }
}
