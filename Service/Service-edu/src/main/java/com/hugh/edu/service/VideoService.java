package com.hugh.edu.service;

import com.hugh.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-12
 */
public interface VideoService extends IService<Video> {

    void deleteByCourseId(String id);
}
