package com.hugh.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/26
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.hugh.acl.mapper")
@ComponentScan("com.hugh")
public class AclBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclBootApplication.class, args);
    }
}
