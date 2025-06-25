package com.example.lotterysystem.controller.handler;

import com.example.lotterysystem.common.errorcode.GlobalErrorCodeConstants;
import com.example.lotterysystem.common.exception.ControllerException;
import com.example.lotterysystem.common.exception.ServiceException;
import com.example.lotterysystem.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;
@Slf4j
@RestControllerAdvice   //捕获全局抛的异常
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<?> serviceException(ServiceException e){
        //打印错误日志
        log.error("serviceException:{}",e);
        //构造错误结果
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ControllerException.class)
    public CommonResult<?> controllerException(ControllerException e){
        //打印错误日志
        log.error("controllerException:{}",e);
        //构造错误结果
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
    }
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> Exception(Exception e){
        //打印错误日志
        log.error("服务异常:{}",e);
        //构造错误结果
        return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
    }
}
