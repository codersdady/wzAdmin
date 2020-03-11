package com.wzAdmin.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.SaveContextOnUpdateOrErrorResponseWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("登录成功");
        SystemUser systemUser= (SystemUser) authentication.getPrincipal();
        Cookie cookie=new Cookie("userid",systemUser.getId());
        Cookie cookie1=new Cookie("username",systemUser.getName());
        Cookie cookie2=new Cookie("email",systemUser.getEmail());
        request.getSession().setAttribute("userid",systemUser.getId());
//        Cookie cookie3=new Cookie("url",systemUser.getUrl());
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
//        response.addCookie(cookie3);
        response.sendRedirect("/index");
        //new DefaultRedirectStrategy().sendRedirect(request, response, "/success");
    }
}
