package com.hugh.edu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hugh.edu.entity.Teacher;
import com.hugh.edu.entity.vo.TeacherQuery;
import com.hugh.edu.service.TeacherService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-05
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public ReturnMessage  findAllTeacher(){
        List<Teacher> lists = teacherService.list(null);
        Map map = new HashMap();
        map.put("items",lists);
        return ReturnMessage.ok().data(map);
    }

    @ApiOperation("删除指定ID的讲师")
    @DeleteMapping("/{id}")
    public ReturnMessage removeTracher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        if(teacherService.removeById(id)){
            return ReturnMessage.ok();
        }else {
            return ReturnMessage.ng();
        }
    }

    @ApiOperation("测试消息返回服务")
    @GetMapping("test")
    public ReturnMessage testReturnMessage(){
        return ReturnMessage.ok();
    }

    /*
        current：当前页
        limit：每页记录数
     */
    @ApiOperation("分页查询教师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public ReturnMessage pageListTeacher(@PathVariable long current, @PathVariable long limit){
        Page<Teacher> page = new Page<>(current,limit);
        // 方法底层的封装会将所有数据封装到page对象中
        teacherService.page(page,null);
        long total = page.getTotal();
        List<Teacher> list = page.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);
        return ReturnMessage.ok().data(map);
    }

    @ApiOperation("分页条件查询教师")
    @PostMapping("pageTeacherByCondition/{current}/{limit}")
    public ReturnMessage pageListTeacherThrows(
            @PathVariable long current,
            @PathVariable long limit,
            @RequestBody(required = false) TeacherQuery teacherVo){
        // 使用@RequestBody注解，即前端使用Json格式传值

        Page<Teacher> page = new Page<>(current,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();

        // 判断条件是否为空，不为空进行条件拼接
        String name = teacherVo.getName();
        Integer level = teacherVo.getLevel();
        String begin = teacherVo.getBegin();
        String end = teacherVo.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_Create");

        teacherService.page(page,wrapper);
        long total = page.getTotal();
        List<Teacher> list = page.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("list",list);
        return ReturnMessage.ok().data(map);
    }


    @ApiOperation("新增教师")
    @PostMapping("saveTeacher")
    public ReturnMessage saveTeacher(@RequestBody Teacher teacher){
        boolean tag = teacherService.save(teacher);
        if (tag){
            return ReturnMessage.ok();
        }else {
            return ReturnMessage.ng();
        }
    }

    @ApiOperation("通过ID查询教师")
    @GetMapping("getTeacher/{id}")
    public ReturnMessage getTeacherById(@PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return ReturnMessage.ok().data("teacher",teacher);
    }

    @ApiOperation("教师修改方法")
    @PostMapping("updateTeacher")
    public ReturnMessage updateTeacher(@RequestBody Teacher teacher){
        boolean tag = teacherService.updateById(teacher);
        if (tag){
            return ReturnMessage.ok();
        }else {
            return ReturnMessage.ng();
        }
    }
}

