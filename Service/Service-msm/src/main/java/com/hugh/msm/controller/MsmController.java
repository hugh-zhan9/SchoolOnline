package com.hugh.msm.controller;

import com.hugh.msm.service.MsmService;
import com.hugh.util.RandomUtil;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by hugh on 2021/1/17
 */
@Api(description = "短信服务接口")
@RestController
@CrossOrigin
@RequestMapping("/edu/msm/")
public class MsmController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("获取短信验证码")
    @GetMapping("getMsm/{phoneNumber}")
    public ReturnMessage getMsmCode (@ApiParam(value = "手机号") @PathVariable String phoneNumber){
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if(!StringUtils.isEmpty(code)) return ReturnMessage.ok();
        // 生成随机数，传递给阿里云发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        // 发送短信
        boolean isSend = msmService.send(phoneNumber, "SMS_209826283", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phoneNumber, code,5, TimeUnit.MINUTES);
            return ReturnMessage.ok();
        } else {
            return ReturnMessage.ng().message("发送短信失败");
        }
    }
}
