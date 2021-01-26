package com.hugh.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hugh.edu.client.VodService;
import com.hugh.edu.entity.Video;
import com.hugh.edu.mapper.VideoMapper;
import com.hugh.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodService vodService;

    @Override
    public void deleteByCourseId(String id) {
        // 删除小节前先删除其中所有视频
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);
        wrapper.select("video_source_id");
        List<Video> videos = baseMapper.selectList(wrapper);
        List<String> videoId = new ArrayList<>();
        for (Video video : videos){
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)){
                videoId.add(videoSourceId);
            }
        }
        if (videoId.size()>0){
            vodService.deleteVideoByList(videoId);
        }
        // 删除小节
        baseMapper.delete(wrapper);
    }
}
