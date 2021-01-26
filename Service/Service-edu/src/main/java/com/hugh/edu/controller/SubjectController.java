package com.hugh.edu.controller;


import com.hugh.edu.entity.Subject;
import com.hugh.edu.entity.subjectVo.FirstSubject;
import com.hugh.edu.service.SubjectService;
import com.hugh.util.ReturnMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hugh
 * @since 2021-01-10
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    private  SubjectService subjectService;

    @ApiOperation(value = "xlxs文件上传")
    @PostMapping("upload")
    public ReturnMessage uploadXlsx(MultipartFile file){
        subjectService.saveFile(file,subjectService);
        return ReturnMessage.ok().message("上传成功");
    }

    @ApiOperation(value = "获取所有的课程分类")
    @GetMapping("getAll")
    public ReturnMessage getAllSubject(){
        List<FirstSubject> subjectList = subjectService.getAllSubject();
        return ReturnMessage.ok().data("subject",subjectList);
    }

    @ApiOperation(value = "获取一级")
    @GetMapping("getParent")
    public ReturnMessage getAllParent(){
        List<Subject> subjects = subjectService.getParentSubject();
        return ReturnMessage.ok().data("parent",subjects);
    }
}

