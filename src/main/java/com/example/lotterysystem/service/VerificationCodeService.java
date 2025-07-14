package com.example.lotterysystem.service;

public interface VerificationCodeService {
    /**
     * 发送验证码
     * @param phoneNumber
     */
    void sendVerificationCode(String phoneNumber);

    /**
     * 从缓存中获取验证码
     * @param phoneNumber
     * @return
     */
    String getVerificationCode(String phoneNumber);

    /**
     * 发送验证码至邮箱
     * @param email
     */
    void sendVerificationEmailCode(String email);
    String getVerificationEmailCode(String email);
}
