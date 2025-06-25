package com.example.lotterysystem;

import com.example.lotterysystem.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void redisTest(){
        stringRedisTemplate.opsForValue().set("key1","value1");
        System.out.println("从redis获取值："+stringRedisTemplate.opsForValue().get("key1"));
    }
    @Test
    void redisUtil(){
        redisUtil.set("key2","value2");
        redisUtil.set("key3","value3",60L);
        System.out.println("has key2" + redisUtil.hasKey("key2"));
        System.out.println(" key3" + redisUtil.get("key3"));
        System.out.println(" key2" + redisUtil.get("key2"));
        redisUtil.del("key2");
        System.out.println(" key3" + redisUtil.get("key3"));
        System.out.println(" key2" + redisUtil.get("key2"));
    }
}
