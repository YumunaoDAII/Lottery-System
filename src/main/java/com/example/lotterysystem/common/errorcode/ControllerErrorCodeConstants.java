package com.example.lotterysystem.common.errorcode;

public interface ControllerErrorCodeConstants {
    //---------人员模块错误码---------
    ErrorCode REGISTER_ERR=new ErrorCode(100,"注册失败");
    ErrorCode LOGIN_ERR=new ErrorCode(101,"登录失败");
    //---------奖品模块错误码---------
    ErrorCode FIND_PRIZE_LIST_ERR=new ErrorCode(200,"查询奖品列表失败");

    //---------活动模块错误码---------
    ErrorCode CREATE_ACTIVITY_ERR=new ErrorCode(300,"参加活动失败");
    ErrorCode FIND_ACTIVITY_LIST_ERROR=new ErrorCode(301,"查询活动列表失败");



    //---------抽奖错误码---------
}
