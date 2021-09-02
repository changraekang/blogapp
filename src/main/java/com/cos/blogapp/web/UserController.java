package com.cos.blogapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	
	@GetMapping("/home")
	public String home() {
		return"home";
	}
	@GetMapping("/loginForm")
	public String loginForm() {
		
		//String login ="/user/login";
		
		return "/user/loginForm" ;
		
	}

	// /login -> login.jsp
	// views/user/login.jsp
	@GetMapping("/joinForm")
	public String joinForm() {
		
		//String login ="/user/login";
		
		return "/user/joinForm" ;
	}
	
	
	@PostMapping("/login")
	public String login( String username, String password) {
		
		System.out.println(username);
		System.out.println(password);
		// 1. username,password 받기
		// 2. DB -> 조회
		// 3. 있으면
		// 4. session에 저장 (전체행저장)
		// 5. mainPage 돌려주기
		
		return "home";
	}
	
	
}
