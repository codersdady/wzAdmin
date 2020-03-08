package com.wzAdmin;

import com.wzAdmin.dao.UserDao;
import com.wzAdmin.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class WzadminApplicationTests {

@Autowired
private UserDao userDao;
@Autowired
private UserMapper userMapper;


    @Test
    void contextLoads() {
//        userDao.getUserNum();
//        System.out.println("first"+userDao.selectUser(1));
//        System.out.println("second"+userDao.selectUser(1));
    }



}
