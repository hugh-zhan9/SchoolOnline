package com.hugh.servicebase.handler;

import com.hugh.servicebase.exception.SchoolException;
import com.hugh.util.ExceptionUtil;
import com.hugh.util.ReturnMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 *
 * Created by hugh on 2021/1/7
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /*
        全局异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ReturnMessage Error(Exception e){
        e.printStackTrace();
        return ReturnMessage.ng().message("执行了全局异常处理类");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ReturnMessage saveError(Exception e){
        e.printStackTrace();
        return ReturnMessage.ng().message("保存失败");
    }

    /*
        特定异常处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ReturnMessage ArithmeticExceptionCatch(Exception e){
        e.printStackTrace();
        return ReturnMessage.ng().message("捕获了ArithmeticException异常");
    }

    /*
        自定义异常处理
     */
    @ExceptionHandler(SchoolException.class)
    @ResponseBody
    public ReturnMessage SchoolExceptionCatch(SchoolException e){
        e.printStackTrace();
        // 异常信息写入日志文件
        // log.error(e.getExceptionMessage());
        // 使用异常处理工具类进行异常写入，要求自定义异常重写了toString方法
        log.error(ExceptionUtil.getMessage(e));
        return ReturnMessage.ng().resultCode(e.getExceptionCode()).message(e.getExceptionMessage());
    }
}
