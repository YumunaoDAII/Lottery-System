package com.example.lotterysystem.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class RegexUtil {
    /**
     * 邮箱验证（优化版本）
     */
    public static boolean checkMail(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, content);
    }

    /**
     * 手机号码验证（优化版本）
     */
    public static boolean checkMobile(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        String regex = "^1[3456789]\\d{9}$";
        return Pattern.matches(regex, content);
    }

    /**
     * 密码验证（仅长度限制）
     */
    public static boolean checkPassword(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        // 允许纯数字，只检查长度6-12位
        String regex = "^[0-9A-Za-z]{6,12}$";
        return Pattern.matches(regex, content);
    }
}