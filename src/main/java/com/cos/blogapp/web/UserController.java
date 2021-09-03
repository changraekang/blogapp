package com.cos.blogapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

@Controller
public class UserController {
	
	private UserRepository userRepository;
	
	
	//DI
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@GetMapping("/test/query/join")
	public void testQueryJoin() {
		
		userRepository.join("cos","1234","cos@nate.com");
		
		
		
	}
	
	
	
	@GetMapping("/test/join")
	public void testJoin() {
		User user = new User();
		user.setUsername("kang");
		user.setPassword("1234");
		user.setEmail("kcr@gmail.com");
		
		// insert into user( username, password, email) values('kang','1234','kcr@gmail.com')
		userRepository.save(user);
		
		
		
	}
	
	
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
	public String login(LoginReqDto dto) {
		
		System.out.println(dto.getUsername());
		System.out.println(dto.getPassword());
		// 1. username,password 받기
		// 2. DB -> 조회
		// 3. 있으면
		// 4. session에 저장 (전체행저장)
		// 5. mainPage 돌려주기
		
		return "home";
	}
	@PostMapping("/join")
	public String join(JoinReqDto dto) {
		
		
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm";
	}
	
}
