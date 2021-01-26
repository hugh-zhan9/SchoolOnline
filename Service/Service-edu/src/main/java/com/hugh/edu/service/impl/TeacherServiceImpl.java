package com.hugh.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Course;
import com.hugh.edu.entity.Teacher;
import com.hugh.edu.entity.vo.TeacherAboutVo;
import com.hugh.edu.mapper.TeacherMapper;
import com.hugh.edu.service.CourseService;
import com.hugh.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-05
 */
@Service()
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private CourseService courseService;

    @Override
    @Cacheable(value = "teacher", key = "'getTeacherList'")
    public List<Teacher> getFamourseTeacher() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        List<Teacher> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public Map getFrontTeacherList(Page page) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        baseMapper.selectPage(page,queryWrapper);

        List<Teacher> list = page.getRecords();
        long total = page.getTotal();
        long size = page.getSize();
        long current = page.getCurrent();
        long pages = page.getPages();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String,Object> map = new HashMap();
        map.put("list",list);
        map.put("total",total);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    @Override
    public TeacherAboutVo getTeacherAbout(String id) {
        Teacher teacher = baseMapper.selectById(id);
        String teacherId = teacher.getId();
        String teacherName = teacher.getName();
        String teacherAvatar = teacher.getAvatar();
        String teacherIntro = teacher.getIntro();
        String teacherCareer = teacher.getCareer();
        Integer teacherLevel = teacher.getLevel();

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        List<Course> courses = courseService.list(queryWrapper);

        TeacherAboutVo teacherAboutVo = new TeacherAboutVo();

        teacherAboutVo.setId(teacherId);
        teacherAboutVo.setName(teacherName);
        teacherAboutVo.setIntro(teacherIntro);
        teacherAboutVo.setCareer(teacherCareer);
        teacherAboutVo.setAvatar(teacherAvatar);
        teacherAboutVo.setCourseList(courses);
        teacherAboutVo.setLevel(teacherLevel);

        return teacherAboutVo;
    }
}
