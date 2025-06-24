package com.example.lotterysystem;

import com.example.lotterysystem.common.pojo.CommonResult;
import com.example.lotterysystem.common.utils.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class JacksonTest {
    @Test
    void jacksonTest(){
        ObjectMapper objectMapper=new ObjectMapper();
        CommonResult<String> result=CommonResult.error(500,"系统错误");
        String str;
        try {
            str=objectMapper.writeValueAsString(result);
            System.out.println(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            CommonResult<String> readResult=objectMapper.readValue(str,CommonResult.class);
            System.out.println(readResult.getMsg()+readResult.getCode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<CommonResult<String>> commonResults= Arrays.asList(
                CommonResult.success("s1"),CommonResult.success("s2")
        );
        try {
            str= objectMapper.writeValueAsString(commonResults);
            System.out.println(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //List反序列
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(List.class,CommonResult.class);
        try {
            commonResults=  objectMapper.readValue(str,javaType);
            for (CommonResult<String> result1:commonResults){
                System.out.println(result1.getData());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void JacksonUtilTest(){
        CommonResult<String> result=CommonResult.success("success");
        String str;
        str= JacksonUtil.writeValueAsString(result);
        System.out.println(str);
        result= JacksonUtil.readValue(str,CommonResult.class);
        System.out.println(result.getData());
        List<CommonResult<String>> commonResults= Arrays.asList(
                CommonResult.success("s1"),CommonResult.success("s2")
        );
        str= JacksonUtil.writeValueAsString(commonResults);
        System.out.println(str);
        JacksonUtil.readListValue(str,CommonResult.class);
        for (CommonResult<String> result1:commonResults){
            System.out.println(result1.getData());
        }
    }

}
