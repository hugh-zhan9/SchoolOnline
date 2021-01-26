package com.hugh.ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hugh on 2021/1/18
 */
@Component
public class PropertiesUtil implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String openId;

    @Value("${wx.open.app_secret}")
    private String openSecret;

    @Value("${wx.open.redirect_url}")
    private String openUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public  void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = openId;
        WX_OPEN_APP_SECRET = openSecret;
        WX_OPEN_REDIRECT_URL = openUrl;
    }
}
