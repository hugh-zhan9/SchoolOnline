package com.hugh.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/15
 */
@ComponentScan("com.hugh")
@MapperScan("com.hugh.cms.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class CmsBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsBootApplication.class,args);
    }
}
