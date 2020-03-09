package com.wzAdmin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzAdmin.dao.UserDao;
import com.wzAdmin.model.LoginUser;

import com.wzAdmin.model.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {


    @Autowired
    private UserDao userDao;
    public SystemUser loginUser(String name){
       SystemUser systemUser=userDao.loginUser(name);
       return systemUser;
    }

}
