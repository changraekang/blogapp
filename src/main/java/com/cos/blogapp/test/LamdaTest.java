package com.cos.blogapp.test;


// 1.8 Lamda
// 1. Method를 넘기는 게 목적
// 2. interface에 Method가 무조건 하나여야함
// 3. code가 간결해지고 type을 몰라도 됨
interface MySupplier {
	void get();
}


public class LamdaTest {
	static void start(MySupplier s) {
		s.get();
	}
	
	public static void main(String [] args ) {
		start(() -> {System.out.println("get함수 호출됨");});
	}
	
	
}
