package com.hugh.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/5
 */
@EnableFeignClients // 服务调用
@EnableDiscoveryClient  // 服务注册
@SpringBootApplication
@ComponentScan("com.hugh")
@MapperScan("com.hugh.edu.mapper")
public class EduBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduBootApplication.class, args);
    }
}

