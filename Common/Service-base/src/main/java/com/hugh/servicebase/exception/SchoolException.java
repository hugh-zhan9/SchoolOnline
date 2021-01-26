package com.hugh.servicebase.exception;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类，需要手动抛出
 *
 * Created by hugh on 2021/1/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Api(description = "自定义异常类")
public class SchoolException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer ExceptionCode;
    @ApiModelProperty(value = "错误信息")
    private String ExceptionMessage;

    @Override
    public String toString() {
        return "SchoolException{" +
                "ExceptionCode=" + ExceptionCode +
                ", ExceptionMessage='" + ExceptionMessage + '\'' +
                '}';
    }
}
