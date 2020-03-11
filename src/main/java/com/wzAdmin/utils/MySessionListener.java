package com.wzAdmin.utils;

import com.wzAdmin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

@WebListener
public class MySessionListener implements HttpSessionListener {
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(60*5);
    }
    @Autowired
    private UserDao userDao;
    public void sessionDestroyed(HttpSessionEvent event) {
        String uid= (String) event.getSession().getAttribute("userid");
        if(uid!=null&&uid.length()!=0){
            userDao.logout(uid);
        }
    }

}
