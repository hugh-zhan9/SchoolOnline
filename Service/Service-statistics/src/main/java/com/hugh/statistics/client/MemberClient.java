package com.hugh.statistics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hugh on 2021/1/23
 */
@Component
@FeignClient("com.hugh.acl.service-user")
public interface MemberClient {

    @GetMapping("/api/ucenter/getTotalUser/{date}")
    int getTotalUser(@PathVariable("date") String date);
}
