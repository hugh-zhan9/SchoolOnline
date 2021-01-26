package com.hugh.edu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.Api;
import lombok.Data;

/**
 * Created by hugh on 2021/1/10
 */
@Api(description = "与excel表格对应的实体类")
@Data
public class SubjectType {

    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
