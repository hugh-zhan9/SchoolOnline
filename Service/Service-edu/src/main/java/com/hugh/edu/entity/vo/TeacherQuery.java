package com.hugh.edu.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by hugh on 2021/1/6
 */
@Api(description = "教师查询VO")
@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称")
    private String name;
    @ApiModelProperty(value = "教师级别")
    private Integer level;
    @ApiModelProperty(value = "开始时间", example="2019-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "结束时间", example="2019-01-01 10:10:10")
    private String end;
}
