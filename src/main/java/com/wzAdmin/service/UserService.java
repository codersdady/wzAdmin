package com.wzAdmin.service;



import com.wzAdmin.dao.UserDao;
import com.wzAdmin.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService{
    @Autowired
    private UserDao userDao;


    public String addUser(SystemUser systemUser){
        String result=userDao.addUser(systemUser);
        return result;
    }


    public SystemUser selectUserById(String id){

        SystemUser systemUser=userDao.selectUserById(id);
        return systemUser;
    }

    public String updateUser(SystemUser user){
        String resule=userDao.updateUser(user);
        return resule;
    }
    public String getImgById(String id){
        String img=userDao.getImgById(id);
        return img;
    }

}
