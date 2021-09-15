package com.cos.blogapp.handler;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = NoSuchElementException.class)
	public void error1(NoSuchElementException e) {
		System.out.println("오류 : " + e.getMessage());
	}
	
}
