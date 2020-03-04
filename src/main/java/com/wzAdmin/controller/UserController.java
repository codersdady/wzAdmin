package com.wzAdmin.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.service.UserService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/user_register")
    public String to_register(@RequestParam(value = "name") String name,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "form_datetime") String birthday,
                              @RequestParam(value = "email") String email,
                              Model model,
                              HttpServletRequest request){
        SystemUser systemUser=new SystemUser();
        systemUser.setName(name);
        systemUser.setPassword(password);
        systemUser.setBirthday(String.valueOf(birthday));
        systemUser.setEmail(email);
        String s=userService.addUser(systemUser);
        if("exist".equals(s)){
            return "register";
        }else{
            return "login";
        }
    }


//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_PERSON')")
    @GetMapping(value ="/account")
    private String account(HttpServletRequest request,
                           Model model,
                           Authentication authentication){

        SystemUser user= (SystemUser) authentication.getPrincipal();
        model.addAttribute("user",user);
        return "/form";

    }

    @GetMapping(value ="/update_user")
    private String update_user(@RequestParam(value = "name")String name,
                               @RequestParam(value = "password")String password,
                               @RequestParam(value="birthday")String birthday,
                               @RequestParam(value="email")String email,
                               HttpServletRequest request,
                               Model model,
                               Authentication authentication){

        SystemUser systemUser= (SystemUser) authentication.getPrincipal();
        systemUser.setName(name);
        systemUser.setPassword(password);
        systemUser.setBirthday(birthday);
        systemUser.setEmail(email);
        userService.updateUser(systemUser);
        authentication.getPrincipal();
        model.addAttribute("user",authentication.getPrincipal());
        model.addAttribute("alert",true);
        return "/form";

    }
}
