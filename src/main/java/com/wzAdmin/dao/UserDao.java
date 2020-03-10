package com.wzAdmin.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzAdmin.config.RedisConfig;
import com.wzAdmin.mapper.UserMapper;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.model.User;
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
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Component
@CacheConfig(cacheNames="userCache")
public class UserDao {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private  RedisTemplate redisTemplate;

//    public SystemUser getUserByName(String name){
//        SystemUser systemUser=userMapper.selectUserByName(name);
//        return systemUser;
//    }

    public SystemUser loginUser(String name){
        SystemUser s=userMapper.selectUserByName(name);
        if(s==null){
            return null;
        }
        if(s.getStatus()==SystemUser.Status.DISABLE){
            return s;
        }
        int re=userMapper.loginUser(name);
        SystemUser systemUser=userMapper.selectUserByName(name);
        if(re==1){
            systemUser.setStatus(1);
        }
        return systemUser;
    }

    public int logout(String id){
        int re=userMapper.logoutUser(id);
        return re;
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
            systemUser.setStatus(1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            systemUser.setUcreate(df.format(new Date()));
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

    public int[] getUserNumByData(){
        String data="0";
        int[] re=new int[5];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-(3*24));
        data=df.format(calendar.getTime());
        int a=userMapper.selectUserNumByData(data);

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-(2*24));
        data=df.format(calendar.getTime());
        int b=userMapper.selectUserNumByData(data);

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        data=df.format(calendar.getTime());
        int c=userMapper.selectUserNumByData(data);

        data=df.format(new Date());
        int d=userMapper.selectUserNumByData(data);


        re[0]=userMapper.selectUserNum()-a-b-c-d;
        re[1]=re[0]+a;
        re[2]=re[1]+b;
        re[3]=re[2]+c;
        re[4]=re[3]+d;


        return re;

    }

    public double[] selectUserSex(){

        double[] re=new double[2];
        double a=userMapper.selectUserNum();
        double b=userMapper.selectUserSex();
        re[0]=(double) Math.round((b/a)*100 * 100) / 100;
        re[1]= (double) Math.round((100-re[0]) * 100) / 100;
        return re;
    }

    public List<int[]> selectDataReport(){
        int[] man=new int[5];
        int[] woman=new int[5];
        int a=0,b=0;
        String data="0";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-(4*24));
        data=df.format(calendar.getTime());
        a=userMapper.selectUserDataSex(data,"1");
        b=userMapper.selectUserDataSex(data,"2");
        man[0]=a;
        woman[0]=b;

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-(3*24));
        data=df.format(calendar.getTime());
        a=userMapper.selectUserDataSex(data,"1");
        b=userMapper.selectUserDataSex(data,"2");
        man[1]=a;
        woman[1]=b;

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-(2*24));
        data=df.format(calendar.getTime());
        a=userMapper.selectUserDataSex(data,"1");
        b=userMapper.selectUserDataSex(data,"2");
        man[2]=a;
        woman[2]=b;

        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,-24);
        data=df.format(calendar.getTime());
        a=userMapper.selectUserDataSex(data,"1");
        b=userMapper.selectUserDataSex(data,"2");
        man[3]=a;
        woman[3]=b;

        data=df.format(new Date());
        a=userMapper.selectUserDataSex(data,"1");
        b=userMapper.selectUserDataSex(data,"2");
        man[4]=a;
        woman[4]=b;

        List<int[]> list=new ArrayList<>();
        list.add(man);
        list.add(woman);

        return list;
    }


}
