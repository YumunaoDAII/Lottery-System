package com.example.lotterysystem.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 邮箱验证（修复版本）
     */
    public static boolean checkMail(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        // 修复后的正则表达式
        String regex = "^[a-zA-Z0-9]+([._-]*[a-zA-Z0-9])*@([a-zA-Z0-9]+[-a-zA-Z0-9]*[a-zA-Z0-9]+\\.){1,63}[a-zA-Z]{2,}$";
        return Pattern.matches(regex, content);
    }

    /**
     * 手机号码以1开头的11位数字
     */
    public static boolean checkMobile(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        String regex = "^1[3|4|5|6|7|8|9][0-9]{9}$";
        return Pattern.matches(regex, content);
    }

    /**
     * 密码强度正则，6到12位
     */
    public static boolean checkPassword(String content){
        if (!StringUtils.hasText(content)) {
            return false;
        }
        String regex= "^[0-9A-Za-z]{6,12}$";
        return Pattern.matches(regex, content);
    }
}