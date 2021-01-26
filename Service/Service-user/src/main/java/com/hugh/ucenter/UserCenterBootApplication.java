package com.hugh.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/17
 */
@ComponentScan("com.hugh")
@MapperScan("com.hugh.ucenter.mapper")
@SpringBootApplication
public class UserCenterBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterBootApplication.class,args);
    }
}
