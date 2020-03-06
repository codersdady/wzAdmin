package com.wzAdmin.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzAdmin.config.RedisConfig;
import com.wzAdmin.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Slf4j
@Component
public class UserDao {

    @Resource
    private  RedisTemplate redisTemplate;

    public SystemUser getUserByName(String name){
        if(redisTemplate.hasKey("name:"+name)){
            String id=redisTemplate.opsForValue().get("name:"+name).toString();
            Map<Object, Object> map=redisTemplate.opsForHash().entries("user:"+id);
            SystemUser systemUser=new SystemUser();
            systemUser.setName(name);
            systemUser.setBirthday((String) map.get("birthday"));
            systemUser.setPassword((String) map.get("password"));
            systemUser.setId(id);
            systemUser.setRole((String) map.get("role"));
            systemUser.setEmail((String) map.get("email"));
            systemUser.setStatus(Integer.valueOf((String) map.get("status")));
//            systemUser.setUrl((String) map.get("img"));
            return systemUser;
        }else {
            return null;
        }
    }

    public SystemUser selectUserById(String id){
//        RedisTemplate<String,Object> redisTemplate1=new RedisTemplate<>();
//        HashOperations<String, Object, Object> defaultHashOperations =redisTemplate1.opsForHash();
//        Map<Object,Object> map=defaultHashOperations.entries("user:"+id);

//        RedisConfig redisConfig=new RedisConfig();
//        redisConfig.redisTemplate().opsForHash()
//        RedisConfig redisConfig=new RedisConfig();
        Map<Object,Object> map=redisTemplate.opsForHash().entries("user:"+id);
        SystemUser systemUser=new SystemUser();
        systemUser.setName((String) map.get("name"));
        systemUser.setEmail((String) map.get("email"));
        systemUser.setStatus(Integer.valueOf((String) map.get("status")));
        systemUser.setRole((String) map.get("role"));
        systemUser.setBirthday((String) map.get("birthday"));
        systemUser.setUrl((String) map.get("img"));
        return systemUser;
    }

    public String addUser(SystemUser systemUser){
        if(redisTemplate.hasKey("name:"+systemUser.getName())){
            log.info("用户名存在->>"+systemUser.getName());
            return "exist";
        }else{
            UUID uuid=UUID.randomUUID();
            redisTemplate.opsForValue().set("name:"+systemUser.getName(),uuid);
            Map<String,String> map=new HashMap<>();
            map.put("name",systemUser.getName());
            map.put("password",new BCryptPasswordEncoder().encode(systemUser.getPassword()));
            map.put("birthday",systemUser.getBirthday());
            map.put("role","PERSON");
            map.put("status","0");
            map.put("email",systemUser.getEmail());
            map.put("img",systemUser.getUrl());
            try{
                redisTemplate.opsForHash().putAll("user:"+uuid,map);
                log.info("注册成功->>"+systemUser.getName());
            }catch (Exception e){
                throw e;
            }


            return "success";
        }

    }

    public String updateUser(SystemUser systemUser){
        Map<String,String> map=new HashMap<>();
        map.put("name",systemUser.getName());
        map.put("password",new BCryptPasswordEncoder().encode(systemUser.getPassword()));
        map.put("birthday",systemUser.getBirthday());
        map.put("email",systemUser.getEmail());

        try{
            redisTemplate.opsForHash().putAll("user:"+systemUser.getId(),map);
            log.info("修改用户信息成功->>"+systemUser.getName());
        }catch (Exception e){
            throw e;
        }
        return "success";
    }

    public String getImgById(String id){
        Map<Object, Object> map=redisTemplate.opsForHash().entries("user:"+id);
        String img= (String) map.get("img");
        return img;
    }
}
