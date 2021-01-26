package com.hugh.statistics.controller;


import com.hugh.statistics.client.MemberClient;
import com.hugh.statistics.entity.StatisticsDaily;
import com.hugh.statistics.service.StatisticsDailyService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.core.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-23
 */
@Api(description = "网站统计数据接口")
@CrossOrigin
@RestController
@RequestMapping("edu/statistics")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;
    @Autowired
    private MemberClient memberClient;

    @ApiOperation("获取所有对象")
    @GetMapping("showData/{type}/{begin}/{end}")
    public ReturnMessage showData(
            @PathVariable("type") String type,
            @PathVariable("begin") String begin,
            @PathVariable("end") String end){
        Map<String,Object> dataMap = statisticsDailyService.getShowData(type,begin,end);
        return ReturnMessage.ok().data(dataMap);
    }

    @ApiOperation("生成统计数据")
    @PostMapping("createDaily/{date}")
    public ReturnMessage createDaily(@PathVariable String date){
        statisticsDailyService.createDaily(date);
        return ReturnMessage.ok().message("添加成功");
    }
}

