package com.hugh.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/20
 */
@EnableFeignClients // 服务调用
@EnableDiscoveryClient  // 服务注册
@MapperScan("com.hugh.order.mapper")
@ComponentScan("com.hugh")
@SpringBootApplication
public class OrderBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderBootApplication.class,args);
    }
}
