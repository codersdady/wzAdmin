package com.wzAdmin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class WzadminApplicationTests {

@Autowired
private RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
        System.out.println(redisTemplate);
    }

}
