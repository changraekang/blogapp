package com.cos.blogapp.web;

import javax.servlet.http.HttpSession;

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
	private HttpSession session;
	
	//DI
	public UserController(UserRepository userRepository, HttpSession session) {
		this.userRepository = userRepository;
		this.session		= session;
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
		
		User userEntitiy = userRepository.mLogin(dto.getUsername(), dto.getPassword()); 
		
		if(userEntitiy == null) {
			return "redirect:/loginForm";
		}else {
			session.setAttribute("principal", userEntitiy);
			return "redirect:/home";
		}
		
	}
	@PostMapping("/join")
	public String join(JoinReqDto dto) {
		
		
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm";
	}
	
}
