package com.hugh.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hugh on 2021/1/17
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.hugh")
@EnableFeignClients
@EnableDiscoveryClient
public class MsmBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmBootApplication.class, args);
    }
}
