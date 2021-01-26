package com.hugh.msm.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hugh on 2021/1/17
 */
public interface MsmService {
    boolean send(String phoneNumber, String sms_180051135, Map<String, Object> param);
}
