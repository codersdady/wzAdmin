package com.wzAdmin.controller;


import com.wzAdmin.model.ReportNum;
import com.wzAdmin.model.SystemUser;
import com.wzAdmin.model.User;
import com.wzAdmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    private String loginPage() throws IOException {
        return "login";
    }

    @GetMapping(value = "/authentication")
    @ResponseBody
    private String authenTication(){
        return "";
    }

    @GetMapping(value = "/index")
    private String index(Model model){
        List<SystemUser> list=userService.getAllUser();
        List<ReportNum> reportNumList=userService.getReport();
        model.addAttribute("UserList",list);
        model.addAttribute("Report",reportNumList);
        return "index";
    }

    @GetMapping(value = "/logout")
    private String logout(){
        return "logout";
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
//    @GetMapping(value = "/admin/index")
//    public String adminPage(){
//        return "admin/admin";
//    }

    @GetMapping(value = "/person")
    public String personPage(){
        return "person";
    }

    @GetMapping("/404")
    public String notFoundPage() {
        return "404";
    }

    @GetMapping("/403")
    public String accessError() {
        return "403";
    }

    @GetMapping("/500")
    public String internalError() {
        return "500";
    }


    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
