package com.wzAdmin.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzAdmin.config.RedisConfig;
import com.wzAdmin.mapper.UserMapper;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.model.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;


@Slf4j
@Component
@CacheConfig(cacheNames="userCache")
public class UserDao {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private  RedisTemplate redisTemplate;

    public SystemUser getUserByName(String name){
        SystemUser systemUser=userMapper.selectUserByName(name);
        return systemUser;
    }


    public SystemUser selectUserById(String id){
        SystemUser systemUser=userMapper.selectUserById(id);
        return systemUser;
    }


    public String addUser(SystemUser systemUser){
        if(userMapper.isExist(systemUser.getName())>0){
            log.info("用户名存在->>"+systemUser.getName());
            return "exist";
        }else {
            String uuid= String.valueOf(UUID.randomUUID());
            systemUser.setId(uuid);
            systemUser.setPassword(new BCryptPasswordEncoder().encode(systemUser.getPassword()));
            systemUser.setRole("USER_UPDATE");
            systemUser.setStatus(0);
            int result=userMapper.addUser(systemUser);
            if(result>0){
                log.info("注册成功->>"+systemUser.getName());
                return "success";
            }else {
                log.info("注册失败");
                return "error";
            }


        }
    }

    public String updateUser(SystemUser systemUser){
        int result=userMapper.updateUserById(systemUser);

            if(result==1){
                log.info("修改用户信息成功->>"+systemUser.getName());
                return "success";
            }else {
                return "error";
            }

    }

    @Cacheable(key = "#id")
    public String getImgById(String id){
        String img=userMapper.selectImgById(id);
        return img;
    }


}
