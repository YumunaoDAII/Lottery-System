package com.example.lotterysystem.common.errorcode;

public interface ServiceErrorCodeConstants {
    //---------人员模块错误码---------
    ErrorCode REGISTER_IS_EMPTY=new ErrorCode(100,"注册信息为空");
    ErrorCode MAIL_ERROR=new ErrorCode(101,"邮箱格式错误");
    ErrorCode PHONE_NUMBER_ERROR=new ErrorCode(102,"手机号格式错误");
    ErrorCode IDENTITY_ERROR=new ErrorCode(103,"身份错误");
    ErrorCode PASSWORD_IS_EMPTY=new ErrorCode(104,"密码为空");
    ErrorCode PASSWORD_ERROR=new ErrorCode(105,"密码错误");
    ErrorCode MAIL_USED=new ErrorCode(106,"邮箱被使用");
    ErrorCode PHONE_NUMBER_USED=new ErrorCode(107,"电话被使用");
    ErrorCode LOGIN_INFO_NOT_EXIST=new ErrorCode(108,"登录信息不存在！");
    ErrorCode LOGIN_NOT_EXIST=new ErrorCode(109,"登录方式不存在！");
    ErrorCode USER_INFO_IS_EMPTY=new ErrorCode(110,"用户信息为空！");
    ErrorCode VERIFICATION_CODE_ERROR=new ErrorCode(111,"验证码错误");
    //---------活动模块错误码---------


    //---------奖品模块错误码---------


    //---------抽奖错误码---------
}
