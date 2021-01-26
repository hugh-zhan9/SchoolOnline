package com.hugh.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.statistics.client.MemberClient;
import com.hugh.statistics.entity.StatisticsDaily;
import com.hugh.statistics.mapper.StatisticsDailyMapper;
import com.hugh.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.util.RandomUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-23
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private MemberClient memberClient;

    @Override
    public void createDaily(String date) {

        int registerNum = memberClient.getTotalUser(date);

        StatisticsDaily statisticsDaily = new StatisticsDaily();

        statisticsDaily.setDateCalculated(date);
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",date);
        baseMapper.delete(queryWrapper);
        int count = baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 因为前端echarts需要的数据结构是json数组，对应的就是后端的list集合。json字符串对应的是map
        // 所以创建两个list集合进行数据的传输
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (StatisticsDaily statisticsDaily : staList){
            dateList.add(statisticsDaily.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("date",dateList);
        dataMap.put("num",numList);
        return dataMap;
    }
}
