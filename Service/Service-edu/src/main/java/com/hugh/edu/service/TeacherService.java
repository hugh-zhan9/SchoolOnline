package com.hugh.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hugh.edu.entity.vo.TeacherAboutVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author hugh
 * @since 2021-01-05
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getFamourseTeacher();

    Map getFrontTeacherList(Page page);

    TeacherAboutVo getTeacherAbout(String id);
}
