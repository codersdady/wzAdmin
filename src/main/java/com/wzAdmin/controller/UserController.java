package com.wzAdmin.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.service.UserService;
import com.wzAdmin.utils.Base64ImageUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Controller
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private Base64ImageUtils base64ImageUtils;

    @RequestMapping("/register")
    public String register(){


        return "register";
    }

    @RequestMapping("/user_register")
    public String to_register(@RequestParam(value = "name") String name,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "form_datetime") String birthday,
                              @RequestParam(value = "email") String email,
                              @RequestParam(value = "file") MultipartFile file,
                              Model model,
                              HttpServletRequest request){

        String content = base64ImageUtils.encodeImageToBase64(file);
        Map<String, String> criteria = new HashMap<>();
        criteria.put("content", content);

        SystemUser systemUser=new SystemUser();
        systemUser.setName(name);
        systemUser.setPassword(password);
        systemUser.setBirthday(String.valueOf(birthday));
        systemUser.setEmail(email);
        systemUser.setUrl("data:image/png;base64,"+content);
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

    @GetMapping(value ="/get_img")
    @ResponseBody
    private String get_img(@RequestParam(value = "id")String id){
        String img=userService.getImgById(id);
//        BASE64Encoder encoder = new BASE64Encoder();
//        imgStr = encoder.encode(img);
//        String re=JSON.toJSONString(img);
        return img;

    }

    @GetMapping(value = "/index1")
    private String index1(){
        return "/index1";
    }
}
