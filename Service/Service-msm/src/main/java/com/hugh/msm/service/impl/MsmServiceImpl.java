package com.hugh.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hugh.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by hugh on 2021/1/17
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String PhoneNumbers, String templateCode, Map<String, Object> param) {
        // 手机号为空直接返回false
        if(StringUtils.isEmpty(PhoneNumbers)){
            return false;
        }
        // 不为空的操作
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI4G1VZZE5tJKqbtetwrAy", "xgxnIXMprdszkxXGDlTFGqoPxX3Dxq");

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        //request.setProtocol(ProtocolType.HTTPS);

        // 设置相关参数
        request.setMethod(MethodType.POST); // 提交方式
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");   //  版本号，不用修改
        request.setAction("SendSms");       // 设置发送短信，不能修改

        // 设置发送相关参数
        request.putQueryParameter("PhoneNumbers",PhoneNumbers);
        request.putQueryParameter("SignName", "晚青的个人博客");       // 签名
        request.putQueryParameter("TemplateCode", templateCode);        // 模板编号
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));     // 传输验证码

        try {
            // 最终发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
