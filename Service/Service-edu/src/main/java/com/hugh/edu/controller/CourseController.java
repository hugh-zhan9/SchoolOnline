package com.hugh.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Course;
import com.hugh.edu.entity.Teacher;
import com.hugh.edu.entity.vo.CoursePublishVo;
import com.hugh.edu.entity.vo.CourseQuery;
import com.hugh.edu.entity.vo.CourseVo;
import com.hugh.edu.service.CourseService;
import com.hugh.util.ReturnMessage;
import com.hugh.util.vo.CourseOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-11
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save")
    public ReturnMessage saveCourse(@RequestBody CourseVo courseVo){
        String id = courseService.saveCourse(courseVo);
        return ReturnMessage.ok().data("id",id);
    }

    @ApiOperation(value = "根据课程ID查询课程信息")
    @GetMapping("getCourse/{id}")
    public ReturnMessage getCourseById(@PathVariable String id){
        CourseVo courseVo = courseService.getCourseById(id);
        return ReturnMessage.ok().data("course",courseVo);
    }

    @ApiOperation(value = "保存修改之后的课程信息")
    @PostMapping("saveCourse/{id}")
    public ReturnMessage saveCourseById(@PathVariable String id, @RequestBody CourseVo courseVo){
        courseVo.setId(id);
        Integer count = courseService.updateCourse(courseVo);
        if (count == 1){
            return ReturnMessage.ok();
        }else {
            return ReturnMessage.ng();
        }
    }

    @ApiOperation(value = "通过ID获取课程发布信息")
    @GetMapping("getPublish/{id}")
    public ReturnMessage getCoursePublishVoById(@PathVariable String id){
        CoursePublishVo course = courseService.getPublishCourseById(id);
        return ReturnMessage.ok().data("course",course);
    }

    @ApiOperation(value = "课程最终发布接口")
    @PostMapping("updateState/{id}")
    public ReturnMessage publishCourse(@PathVariable String id){
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Course course = courseService.getOne(wrapper);
        course.setStatus("Normal");
        courseService.saveOrUpdate(course);
        return ReturnMessage.ok().message("发布成功");
    }



    @ApiOperation("分页条件查询课程")
    @PostMapping("pageCourse/{current}/{limit}")
    public ReturnMessage pageListTeacherThrows(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) CourseQuery courseVo){
        // 使用@RequestBody注解，即前端使用Json格式传值

        Page<Course> page = new Page<>(current,limit);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        courseService.page(page,wrapper);
        Long total = page.getTotal();
        List<Course> list = page.getRecords();
        Map<String,Object> map = new HashMap();
        map.put("courses",list);
        map.put("total",total);
        return ReturnMessage.ok().data(map);
    }

    @ApiOperation("删除课程信息")
    @DeleteMapping("delete/{id}")
    public ReturnMessage deleteCourseById(@PathVariable String id){
        boolean flag = courseService.deleteCourseById(id);
        return ReturnMessage.ok();
    }

    @ApiOperation("通过获取生成订单所需的课程信息")
    @GetMapping("getCourseForOrder/{id}")
    public CourseOrderVo getCourseForOrderById(@PathVariable("id") String id){
        CoursePublishVo course = courseService.getPublishCourseById(id);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(course,courseOrderVo);
        return courseOrderVo;
    }
}

