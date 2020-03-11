package com.wzAdmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableCaching
@MapperScan(value = "com.wzAdmin.mapper")
@ServletComponentScan
public class WzadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzadminApplication.class, args);
    }

}
