package com.wzAdmin.service;

import com.wzAdmin.model.LoginUser;
import com.wzAdmin.model.SystemUser;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//public class UserDetailsServiceImpl  {
    @Autowired
    LoginService loginService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SystemUser systemUser =loginService.loginUser(s);
        if(systemUser==null){
            throw new AuthenticationNotSupportedException("用户不存在");
        }else if(systemUser.getStatus()==SystemUser.Status.LOCKED){
            throw new LockedException("用户被锁定");
        }else if(systemUser.getStatus()==SystemUser.Status.DISABLE){
            throw new LockedException("用户被冻结");
        }
        LoginUser loginUser=new LoginUser();
        BeanUtils.copyProperties(systemUser,loginUser);
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(systemUser.getRole()));
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        String [] spString = systemUser.getRole().split("\\s+");
        for(String a:spString){
            authorities.add(new SimpleGrantedAuthority(a));
        }

        loginUser.setList(authorities);
        return loginUser;
    }
}
