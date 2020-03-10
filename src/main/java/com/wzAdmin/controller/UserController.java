package com.wzAdmin.controller;
import com.alibaba.fastjson.JSONArray;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.service.UserService;
import com.wzAdmin.utils.Base64ImageUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
                              @RequestParam(value = "sex")String sex,
                              @RequestParam(value = "file") MultipartFile file,
                              Model model,
                              HttpServletRequest request){



        if(name==null||name.equals("")){
            model.addAttribute("log","用户名不能为空");
            return "/register";
        }
        if(password==null||password.equals("")){
            model.addAttribute("log","用户密码不能为空");
            return "/register";
        }
        if(email==null||email.equals("")){
            model.addAttribute("log","用户邮箱不能为空");
            return "/register";
        }
        SystemUser systemUser=new SystemUser();
        systemUser.setName(name);
        systemUser.setPassword(password);
        systemUser.setBirthday(String.valueOf(birthday));
        systemUser.setEmail(email);
        systemUser.setSex(sex);
        if(file==null||file.isEmpty()){
            systemUser.setUrl("");
        }else {
            String content = base64ImageUtils.encodeImageToBase64(file);
            systemUser.setUrl("data:image/png;base64,"+content);
        }

        String s=userService.addUser(systemUser);
        if("exist".equals(s)){
            model.addAttribute("log","用户名已存在！");
            return "/register";
        }else{
            return "/login";
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
        return img;
    }

    @GetMapping(value="/get_user_num")
    @ResponseBody
    private int[] get_user_num(){
        int[] num=userService.getUserNumByData();
        return num;
    }

    @GetMapping(value = "/get_sex")
    @ResponseBody
    private double[] get_sex(){
        double[] num=userService.getUserSex();
        return num;
    }

    @GetMapping(value="/get_data_report")
    @ResponseBody
    private List<int[]> getReport(){
        List<int[]> list=userService.getDataReport();
        return list;
    }


}
