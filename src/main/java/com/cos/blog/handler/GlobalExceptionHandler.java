package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // 어디에서든 Exception이 발생해도 이곳으로 오게 하기 위해서
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleArgumentException(Exception e) {
        return "<h1>" + e.getMessage() + "</h1>";
    }
}
