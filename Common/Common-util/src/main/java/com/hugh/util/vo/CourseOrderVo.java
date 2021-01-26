package com.hugh.util.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by hugh on 2021/1/22
 */
@Data
@ApiModel(value="Course对象", description="课程")
public class CourseOrderVo {

    private String id;
    private String title;
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
    private String teacherName;
    private String subjectLevelOne;
    private String subjectLevelTwo;

}
