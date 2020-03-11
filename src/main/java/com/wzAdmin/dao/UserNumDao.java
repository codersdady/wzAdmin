package com.wzAdmin.dao;

import com.wzAdmin.mapper.UserNumMapper;
import com.wzAdmin.model.LoginNum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UserNumDao {
    @Autowired
    private UserNumMapper userNumMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public int addNum(String userid){
        int re=userNumMapper.updateNum(userid);
        if(re==0){
            userNumMapper.addNum(userid);
        }
        return 1;
    }


    public List<Map<String,String>> selectNum(){
        List<Map<String,String>> list=userNumMapper.selectNum();
        return list;

    }
}
