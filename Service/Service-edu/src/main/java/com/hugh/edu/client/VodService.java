package com.hugh.edu.client;

import com.hugh.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by hugh on 2021/1/15
 */
@FeignClient(name = "com.hugh.acl.service-vod", fallback = VodServiceOverClient.class)
@Component
public interface VodService {

    // 这里的@PathVariable注解一定要指定变量名称，否则可能报错
    @DeleteMapping("/edu/vodControl/delete/{id}")
    public abstract ReturnMessage deleteAliyunVideoById(@PathVariable("id") String id);

    @DeleteMapping("/edu/vodControl/deleteVideos")
    public abstract ReturnMessage deleteVideoByList(@RequestParam("videoIDList") List<String> videoIDList);
}
