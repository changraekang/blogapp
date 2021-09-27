package com.cos.blogapp.handler.ex;



/**
 * 
 * @author 강창래 2021-09-16
 * 1. id를 못 찾았을 때 사용
 * 2. 비동기 상황에서 사용
 * 
 *
 */
public class MyAPINotFoundException extends RuntimeException {

	public MyAPINotFoundException (String msg) {
		super(msg);	
	}
	
}
