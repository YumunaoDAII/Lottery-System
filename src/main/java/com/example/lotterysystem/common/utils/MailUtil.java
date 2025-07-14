package com.example.lotterysystem.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMailCode(String mail,String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mail); // 发送到自己邮箱测试
        message.setSubject("抽奖系统登录验证码");
        message.setText("您正在进行抽奖系统登录验证，验证码为："+code+"，请勿泄漏给他人！");
        try {
            javaMailSender.send(message);
            System.out.println("发送成功");
        } catch (Exception e) {
            System.err.println("发送失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
