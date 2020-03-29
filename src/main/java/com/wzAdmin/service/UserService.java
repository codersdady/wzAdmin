package com.wzAdmin.service;



import com.wzAdmin.dao.UserDao;
import com.wzAdmin.dao.UserNumDao;
import com.wzAdmin.model.ReportNum;
import com.wzAdmin.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserNumDao userNumDao;


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
//        long a= System.currentTimeMillis();
        String img=userDao.getImgById(id);
//        System.out.println(System.currentTimeMillis()-a+"毫秒");
        return img;
    }
    public int[] getUserNumByData(){
        return userDao.getUserNumByData();
    }
    public double[] getUserSex(){
        return userDao.selectUserSex();
    }
    public List<int[]> getDataReport(){
        List<int[]> list=userDao.selectDataReport();
        return list;
    }
    public List<SystemUser> getAllUser(){
        List<SystemUser> list=userDao.selectAllUser();
        return list;
    }
    public List<ReportNum> getReport(){
        List<Map<String,String>> list=userNumDao.selectNum();
        List<ReportNum> list1=new ArrayList<>();
        for(Map<String,String> map:list){
            ReportNum reportNum=new ReportNum();
            reportNum.setUserid(map.get("userid"));
            reportNum.setNum(String.valueOf(map.get("num")));
            reportNum.setName(userDao.selectUserById(reportNum.getUserid()).getName());
            list1.add(reportNum);
        }
        return list1;
    }
    public void close_user(String id){
        userDao.close_user(id);
    }
    public void up_user(String id){
        userDao.up_user(id);
    }

}
