package com.hugh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by hugh on 2021/1/24
 */
@SpringBootApplication
@EnableDiscoveryClient  // 服务注册
public class ApiGateWayBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGateWayBootApplication.class,args);
    }
}
