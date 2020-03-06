package com.wzAdmin.config;

import com.wzAdmin.dao.UserDao;
import com.wzAdmin.security.CoreqiLogoutSuccessHandler;
import com.wzAdmin.security.MyLoginFailureHandler;
import com.wzAdmin.security.MyLoginSuccessHandler;
import com.wzAdmin.service.UserDetailsServiceImpl;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfg extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
    @Bean
    UserDetailsService userDetailsServiceI(){
        return new UserDetailsServiceImpl();
    }


    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;
    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;
    @Autowired
    private CoreqiLogoutSuccessHandler coreqiLogoutSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceI()).passwordEncoder(passwordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/vendor/**","/js/**"
                ,"/images/**","/fonts/**","/css/**");
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/account").hasAnyAuthority("USER_UPDATE")
                .antMatchers("/user_register").permitAll()
                .antMatchers("/update_user").hasAnyAuthority("list-unstyled navbar__sub-list js-sub-list")
                .antMatchers("/index1").permitAll()
//                .antMatchers("/index2").permitAll()
//                .antMatchers("/index.html").hasRole("ADMIN")
//                .antMatchers("/index2.html").hasAnyRole("USER","ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout()   //退出登录相关配置
                .logoutUrl("/logout")   //自定义退出登录页面
                .logoutSuccessHandler(coreqiLogoutSuccessHandler) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
                    //.logoutSuccessUrl("/index") //退出成功后跳转的页面
                .deleteCookies("username")    //退出时要删除的Cookies的名字
                .deleteCookies("userid")
                .deleteCookies("email")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authentication")
//                .defaultSuccessUrl("/success")
                .successHandler(myLoginSuccessHandler)
                .failureHandler(myLoginFailureHandler)
//                .successForwardUrl("/index")
//                .failureForwardUrl("/error")
                .and()
                .csrf().disable();


    }




}
