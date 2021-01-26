package com.hugh.order.client;

import com.hugh.util.vo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hugh on 2021/1/22
 */
@Component
@FeignClient("com.hugh.acl.service-edu")
public interface EduClient {

    @GetMapping("/edu/course/getCourseForOrder/{id}")
    CourseOrderVo getCourseForOrderById(@PathVariable("id") String id);

}
