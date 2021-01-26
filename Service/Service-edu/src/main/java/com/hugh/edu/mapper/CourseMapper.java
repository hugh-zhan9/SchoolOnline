package com.hugh.edu.mapper;

import com.hugh.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hugh.edu.entity.vo.CourseAboutVo;
import com.hugh.edu.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author hugh
 * @since 2021-01-11
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo getCoursePublishVo(String id);

    CourseAboutVo getCourseAboutByID(String id);
}
