package com.example.lotterysystem.common.utils;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

public class CaptchaUtil {
    /**
     * 生成随机验证码
     * @param length 几位
     * @return
     */
    public static String getCaptchaUtil(int length){
        //自定义纯数字验证码
        RandomGenerator randomGenerator=new RandomGenerator("0123456789",length);
        LineCaptcha lineCaptcha = cn.hutool.captcha.CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        //重新生成code
        lineCaptcha.createCode();
        return lineCaptcha.getCode();

    }
}
