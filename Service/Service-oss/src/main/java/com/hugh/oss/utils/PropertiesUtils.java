package com.hugh.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 工具类，用来读取配置文件信息
 *
 * Created by hugh on 2021/1/10
 */
@Component
// 初始化Bean时执行操作
public class PropertiesUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}") //属性注入注解
    private String endpoint;

    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.file.buckName}")
    private String buckName;

    // 定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCK_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCK_NAME = buckName;
    }
}
