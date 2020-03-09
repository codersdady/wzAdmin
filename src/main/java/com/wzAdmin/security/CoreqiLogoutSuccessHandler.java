package com.wzAdmin.security;

import com.wzAdmin.dao.UserDao;
import com.wzAdmin.model.SystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CoreqiLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserDao userDao;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SystemUser systemUser= (SystemUser) authentication.getPrincipal();
        userDao.logout(systemUser.getId());
        log.info("退出成功");
        httpServletResponse.sendRedirect("/index");
    }
}
