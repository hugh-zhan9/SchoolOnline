package com.hugh.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Course;
import com.hugh.edu.entity.CourseDescription;
import com.hugh.edu.entity.Teacher;
import com.hugh.edu.entity.vo.*;
import com.hugh.edu.mapper.CourseMapper;
import com.hugh.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hugh.servicebase.exception.SchoolException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hugh
 * @since 2021-01-11
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private TeacherService teacherService;

    @Override
    public String saveCourse(CourseVo courseVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseVo,course);
        int insert = baseMapper.insert(course);
        if(insert==0){
            throw new SchoolException(20001,"保存失败");
        }
        String courseId = course.getId();
        CourseDescription description = new CourseDescription();
        description.setDescription(courseVo.getDescription());
        description.setId(courseId);
        courseDescriptionService.save(description);
        return courseId;
    }

    @Override
    public CourseVo getCourseById(String id) {
        Course course = baseMapper.selectById(id);
        CourseDescription description = courseDescriptionService.getById(id);
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(course,courseVo);
        courseVo.setDescription(description.getDescription());
        return courseVo;
    }

    @Override
    public Integer updateCourse(CourseVo courseVo) {
        String courseId = courseVo.getId();
        Course course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(courseVo,course);
        Integer count = baseMapper.updateById(course);
        CourseDescription description = new CourseDescription();
        if(count==0){
            throw new SchoolException(20001,"保存失败");
        }

        description.setDescription(courseVo.getDescription());
        description.setId(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        if (courseDescription!=null){
            courseDescriptionService.updateById(description);
        }else {
            courseDescriptionService.save(description);
        }
        return count;
    }

    @Override
    public CoursePublishVo getPublishCourseById(String id) {
        CoursePublishVo course = baseMapper.getCoursePublishVo(id);
        System.out.println(course);
        return course;
    }

    @Override
    @Transactional
    public boolean deleteCourseById(String id) {
        // 删除对应的小节
        videoService.deleteByCourseId(id);
        // 删除对应的章节
        chapterService.deleteByCourseId(id);
        // 删除对应的课程
        baseMapper.deleteById(id);
        return false;
    }

    @Override
    @Cacheable(value = "course", key = "'getCourse'")
    public List<Course> getTopCourse() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("gmt_create");
        wrapper.ne("status","Draft");
        wrapper.last("limit 8");
        List<Course> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public CourseAboutVo getCourseAbout(String id) {
        // 自定义SQL语句写
        CourseAboutVo courseAboutVo = baseMapper.getCourseAboutByID(id);
        return courseAboutVo;
    }

    @Override
    public Map getCoursePage(Page<Course> page, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            queryWrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create");
        }

        baseMapper.selectPage(page,queryWrapper);

        List<Course> list = page.getRecords();
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
}
