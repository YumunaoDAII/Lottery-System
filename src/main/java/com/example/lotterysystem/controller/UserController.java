package com.example.lotterysystem.controller;

import com.example.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.example.lotterysystem.common.exception.ControllerException;
import com.example.lotterysystem.common.pojo.CommonResult;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.example.lotterysystem.controller.param.UserEmailLoginParam;
import com.example.lotterysystem.controller.param.UserMessageLoginParam;
import com.example.lotterysystem.controller.param.UserPasswordLoginParam;
import com.example.lotterysystem.controller.param.UserRegisterParam;
import com.example.lotterysystem.controller.result.UserLoginResult;
import com.example.lotterysystem.controller.result.UserRegisterResult;
import com.example.lotterysystem.service.UserService;
import com.example.lotterysystem.service.VerificationCodeService;
import com.example.lotterysystem.service.dto.UserLoginDTO;
import com.example.lotterysystem.service.dto.UserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {
    private static final Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    /**
     * 注册
     */
    @RequestMapping("/register")
    public CommonResult<UserRegisterResult> userRegister( @Validated @RequestBody UserRegisterParam param){
        //日志打印
        logger.info("userRegister UserRegisterParam:{}", JacksonUtil.writeValueAsString(param));
        //调用service层
        UserRegisterDTO userRegisterDTO = userService.register(param);
        logger.info("userRegister userRegisterDTO:{}",userRegisterDTO);
        return CommonResult.success(convertToUserRegisterResult(userRegisterDTO));
    }

    /**
     * 发送验证码服务
     * @param phoneNumber
     * @return
     */
    @RequestMapping("/verification-code/send")
    public CommonResult<Boolean> sendVerificationCode(String phoneNumber){
        logger.info("phoneNumber:{}",phoneNumber);
        verificationCodeService.sendVerificationCode(phoneNumber);
        return CommonResult.success(Boolean.TRUE);
    }
    @RequestMapping("/verification-code/sendMail")
    public CommonResult<Boolean> sendVerificationEmailCode(String email){
        logger.info("email:{}",email);
        verificationCodeService.sendVerificationEmailCode(email);
        return CommonResult.success(Boolean.TRUE);
    }
    @RequestMapping("/password/login")
    public CommonResult<UserLoginResult> UserPasswordLogin(
            @Validated @RequestBody UserPasswordLoginParam param){
        logger.info("UserPasswordLogin UserPasswordLoginParam:{}",
                JacksonUtil.writeValueAsString(param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(convertToUserLoginResult(userLoginDTO));
    }

    private UserLoginResult convertToUserLoginResult(UserLoginDTO userLoginDTO) {
        if (null==userLoginDTO){
            throw new ControllerException(ControllerErrorCodeConstants.LOGIN_ERR);
        }
        UserLoginResult result=new UserLoginResult();
        result.setToken(userLoginDTO.getToken());
        result.setIdentity(userLoginDTO.getIdentity().name());
        return result;
    }

    @RequestMapping("/message/login")
    public CommonResult<UserLoginResult> UserMessageLogin(
            @Validated @RequestBody UserMessageLoginParam param){
        logger.info("UserMessageLogin UserMessageLoginParam:{}",
                JacksonUtil.writeValueAsString(param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(convertToUserLoginResult(userLoginDTO));
    }
    @RequestMapping("/email/login")
    public CommonResult<UserLoginResult> UserEmailLogin(
            @Validated @RequestBody UserEmailLoginParam param){
        logger.info("UserEmailLogin UserEmailLoginParam:{}",
                JacksonUtil.writeValueAsString(param));
        UserLoginDTO userLoginDTO = userService.login(param);
        return CommonResult.success(convertToUserLoginResult(userLoginDTO));
    }




    private UserRegisterResult convertToUserRegisterResult(UserRegisterDTO userRegisterDTO) {
        UserRegisterResult result = new UserRegisterResult();
        if (null==userRegisterDTO){
            throw new ControllerException(ControllerErrorCodeConstants.REGISTER_ERR);
        }
        result.setUserId(userRegisterDTO.getUserId());
        return result;
    }
}
