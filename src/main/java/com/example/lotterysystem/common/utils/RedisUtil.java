package com.example.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,String value){
        try {
            stringRedisTemplate.opsForValue().set(key,value);
            return true;
        }catch (Exception e){
            log.error("RedisUtil err,set({},{})",key,value,e);
            return false;
        }
    }

    /**
     * 设置值（设置过期时间）
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key,String value,Long time){
        try {
            stringRedisTemplate.opsForValue().set(key,value,time, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error("RedisUtil err,set({},{},{})",key,value,time,e);
            return false;
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key){
        try {
            return  StringUtils.hasText(key) ?stringRedisTemplate.opsForValue().get(key):null;
        }catch (Exception e){
            log.error("RedisUtil err,get({})",key,e);
            return null;
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean del(String... key){
        try {
            if (null!=key&&key.length>0){
                if (key.length==1){
                    stringRedisTemplate.delete(key[0]);
                }else {
                    stringRedisTemplate.delete(
                            (Collection<String>) CollectionUtils.arrayToList(key));
                }
            }
            return true;
        }catch (Exception e){
            log.error("RedisUtil err,del({})",key,e);
            return false;
        }
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try {
            return StringUtils.hasText(key) ?stringRedisTemplate.hasKey(key):false;
        }catch (Exception e){
            log.error("RedisUtil err,hasKey({})",key,e);
            return false;
        }
    }

}
