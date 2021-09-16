package com.cos.blogapp.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.Script;
// @ControllerAdvice 는 1. ExceptionHandling 2. @Controller의 기능까지 수행
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류 : " + e.getMessage());
		return Script.href("/", e.getMessage());
	}
	
}
