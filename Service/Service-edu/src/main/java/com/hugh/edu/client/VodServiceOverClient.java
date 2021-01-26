package com.hugh.edu.client;

import com.hugh.util.ReturnMessage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Hystrix 熔断类，当服务出错时的执行类
 *
 * Created by hugh on 2021/1/15
 */
@Component
public class VodServiceOverClient implements VodService {
    @Override
    public ReturnMessage deleteAliyunVideoById(String id) {
        return ReturnMessage.ng().message("删除失败");
    }

    @Override
    public ReturnMessage deleteVideoByList(List<String> videoIDList) {
        return ReturnMessage.ng().message("删除失败");
    }
}
