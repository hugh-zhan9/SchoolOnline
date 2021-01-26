package com.hugh.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.client.OrderService;
import com.hugh.edu.entity.Course;
import com.hugh.edu.entity.Teacher;
import com.hugh.edu.entity.vo.ChapterVo;
import com.hugh.edu.entity.vo.CourseAboutVo;
import com.hugh.edu.entity.vo.CourseFrontVo;
import com.hugh.edu.entity.vo.TeacherAboutVo;
import com.hugh.edu.service.ChapterService;
import com.hugh.edu.service.CourseService;
import com.hugh.edu.service.TeacherService;
import com.hugh.util.JwtUtils;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hugh on 2021/1/15
 */
@Api(description = "前台获取数据接口")
@RestController
@RequestMapping("edu/frontData")
public class FrontDataController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderService orderService;

    @ApiOperation("前台获取热门课程和明星讲师")
    @GetMapping("getData")
    public ReturnMessage getFrontData(){
        List<Teacher> teacherList = teacherService.getFamourseTeacher();
        List<Course> courseList = courseService.getTopCourse();
        return ReturnMessage.ok().data("teacherList",teacherList).data("courseList",courseList);
    }

    @ApiOperation("前端分页查询教师接口")
    @GetMapping("getTeacher/{current}/{limit}")
    public ReturnMessage getTeacherList(@PathVariable("current") int current, @PathVariable("limit") int limit){
        Page<Teacher> page = new Page<>(current,limit);
        Map teacherMap = teacherService.getFrontTeacherList(page);
        return ReturnMessage.ok().data(teacherMap);
    }

    @ApiOperation("通过ID获取教师详细信息")
    @GetMapping("getTeacher/{id}")
    public ReturnMessage getTeacherAbout(@PathVariable String id){
        TeacherAboutVo teacherAboutVo = teacherService.getTeacherAbout(id);
        return ReturnMessage.ok().data("teacher",teacherAboutVo);
    }

    @ApiOperation("通过ID获取课程详细信息")
    @GetMapping("getCourse/{id}")
    public ReturnMessage getCourseAbout(@PathVariable String id, HttpServletRequest request){
        CourseAboutVo courseAboutVo = courseService.getCourseAbout(id);
        List<ChapterVo> chapterVo = chapterService.getChaptersByCourseId(id);
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        boolean flag = false;
        if (!StringUtils.isEmpty(userId)){
            flag = orderService.getOrderStatus(id, JwtUtils.getMemberIdByJwtToken(request));
        }
        return ReturnMessage.ok().data("course",courseAboutVo).data("chapter",chapterVo).data("isBuy",flag);

    }

    @ApiOperation("获取所有一级分类")
    @GetMapping("getSubject")
    public ReturnMessage getSubject(){
        CourseAboutVo courseAboutVo = courseService.getCourseAbout("");
        return ReturnMessage.ok().data("course",courseAboutVo);
    }

    @ApiOperation("按条件分页查询课程")
    @PostMapping("getCourse/{current}/{limit}")
    public ReturnMessage getTeacherList(@PathVariable("current") int current, @PathVariable("limit") int limit,
                                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<Course> page = new Page(current,limit);
        Map map = courseService.getCoursePage(page,courseFrontVo);
        return ReturnMessage.ok().data(map);
    }
}
