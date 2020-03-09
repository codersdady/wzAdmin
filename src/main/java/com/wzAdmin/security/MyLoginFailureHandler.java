package com.wzAdmin.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.effect.Bloom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

@Component
@Slf4j
public class MyLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {




    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        logger.info(exception.toString());
        //request.getRequestDispatcher("/login").forward(request, response);
        String error="出现错误了！";
        if(exception.toString().contains("Bad credentials")){
           error="密码错误！";
        }else if(exception.toString().contains("用户不存在")){
             error="用户不存在！";
        }else if(exception.toString().contains("用户被锁定")){
             error="用户被锁定！";

        }else if(exception.toString().contains("用户被冻结")){
             error="用户被冻结！";

        }
        response.sendRedirect("/login?error="+ URLEncoder.encode(error, "UTF-8"));
    }
}
