package com.hugh.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hugh on 2021/1/22
 */
@Component
@FeignClient(name = "com.hugh.acl.service-order")
public interface OrderService {

    @GetMapping("edu/order/getOrderStatus/{courseId}/{userId}")
    boolean getOrderStatus(@PathVariable("courseId") String courseId, @PathVariable("userId") String userId);
}
