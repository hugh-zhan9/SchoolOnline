package com.hugh.order.client;

import com.hugh.util.vo.MemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hugh on 2021/1/22
 */
@Component
@FeignClient("com.hugh.acl.service-user")
public interface UserClient {

    @GetMapping("/api/ucenter/getUserInfo/{id}")
    MemberVo getUserInfo(@PathVariable("id") String id);
}
