package com.hugh.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.hugh.edu.entity.Course;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by hugh on 2021/1/19
 */
@Data
@ApiModel("前端讲师详细信息类")
public class TeacherAboutVo {

    @ApiModelProperty(value = "讲师ID")
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "教师级别")
    private Integer level;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "讲师课程")
    private List<Course> courseList;
}
