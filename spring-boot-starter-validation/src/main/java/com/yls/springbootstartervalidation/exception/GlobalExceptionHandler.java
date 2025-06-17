package com.yls.springbootstartervalidation.exception;
import com.yls.springbootstartervalidation.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //接收错误的类型
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleGeneralException(Exception e) {
        return Result.error("服务器错误: " + e.getMessage());
    }
}
