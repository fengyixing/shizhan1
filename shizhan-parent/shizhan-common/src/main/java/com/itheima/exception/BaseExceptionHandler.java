package com.itheima.exception;


import com.itheima.util.Result;
import com.itheima.util.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice//开启spring的公共异常处理
public class BaseExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
        return new Result(false, StatusCode.ERROR,"系统繁忙"+e.getMessage());
    }
}
