package com.hugh.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by hugh on 2021/1/23
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.hugh")
@MapperScan("com.hugh.statistics.mapper")
@EnableFeignClients // 服务调用
@EnableDiscoveryClient  // 服务注册
public class StatisticBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticBootApplication.class,args);
    }
}
