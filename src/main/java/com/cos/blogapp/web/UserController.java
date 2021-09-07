package com.cos.blogapp.web;

import javax.servlet.http.HttpSession;  

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession session;
	
	//DI

	
	@GetMapping({"/","/home"})
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
		
		if(dto.getUsername() ==  null ||
		   dto.getPassword() ==  null ||
		   dto.getEmail()	 ==  null ||
		   !dto.getUsername().equals("")||
		   !dto.getPassword().equals("")||
		   !dto.getEmail().equals("")
		   
		) {
		   return "error/error";
		}
		
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm";
	}
	
}
