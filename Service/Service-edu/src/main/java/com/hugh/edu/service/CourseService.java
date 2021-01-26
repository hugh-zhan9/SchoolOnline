package com.hugh.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hugh.edu.entity.vo.CourseAboutVo;
import com.hugh.edu.entity.vo.CourseFrontVo;
import com.hugh.edu.entity.vo.CoursePublishVo;
import com.hugh.edu.entity.vo.CourseVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-11
 */
public interface CourseService extends IService<Course> {

    String saveCourse(CourseVo courseVo);

    CourseVo getCourseById(String id);

    Integer updateCourse(CourseVo courseVo);

    CoursePublishVo getPublishCourseById(String id);

    boolean deleteCourseById(String id);

    List<Course> getTopCourse();

    CourseAboutVo getCourseAbout(String id);

    Map getCoursePage(Page<Course> page, CourseFrontVo courseFrontVo);
}
