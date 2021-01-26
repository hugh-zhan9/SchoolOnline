package com.hugh.statistics.schedule;

import com.hugh.statistics.entity.StatisticsDaily;
import com.hugh.statistics.service.StatisticsDailyService;
import com.hugh.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 明天凌晨12点自动更新前一天的注册数
 *
 * Created by hugh on 2021/1/23
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // 明天晚上12点进行一次更新
    @Scheduled(cron = "0 0 0 * * ? ")
    public void task1(){
        String date = DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        statisticsDailyService.createDaily(date);
    }
}
