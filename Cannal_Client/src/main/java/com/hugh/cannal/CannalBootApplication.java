package com.hugh.cannal;

import com.hugh.cannal.client.CannalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * Created by hugh on 2021/1/23
 */
@SpringBootApplication
public class CannalBootApplication implements CommandLineRunner {
    @Resource
    private CannalClient cannalClient;

    public static void main(String[] args) {
        SpringApplication.run(CannalBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        cannalClient.run();
    }
}
