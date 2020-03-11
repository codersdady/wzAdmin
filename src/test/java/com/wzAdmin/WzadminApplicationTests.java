package com.wzAdmin;

import com.wzAdmin.dao.UserDao;
import com.wzAdmin.dao.UserNumDao;
import com.wzAdmin.mapper.UserMapper;
import com.wzAdmin.mapper.UserNumMapper;
import com.wzAdmin.model.LoginNum;
import com.wzAdmin.model.ReportNum;
import com.wzAdmin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class WzadminApplicationTests {

@Autowired
private UserDao userDao;
@Autowired
private UserMapper userMapper;
@Autowired
private UserNumDao userNumDao;
@Autowired
private UserService userService;


    @Test
    void contextLoads() {
//        userDao.getUserNum();
//        System.out.println("first"+userDao.selectUser(1));
//        System.out.println("second"+userDao.selectUser(1));
//        List<int[]> a=userDao.selectDataReport();

//        int a=userNumDao.addNum("af4aee46-2d82-41ac-ac8d-8f2fd7974baa");
//        int b=userNumDao.addNum("af4aee46-2d82-41ac-ac8d-8f2fd7974baa");
        List<ReportNum> list=userService.getReport();
        System.out.println(list);

    }



}
