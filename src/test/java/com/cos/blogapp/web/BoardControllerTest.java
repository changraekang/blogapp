package com.cos.blogapp.web;

import org.junit.jupiter.api.Test;

import com.cos.blogapp.domain.board.Board;

public class BoardControllerTest {
	@Test
	public void ExeptionTest() {
		try {
			Board b = null;
			System.out.println(b.getContent());
		} catch (Exception e ) {
		System.out.println(e);
		System.out.println("오류가 났어요");
	     
		}
		
	}
	
	@Test
	public void ExeptionTest2() throws Exception {
		throw new Exception();
	}
	
}
