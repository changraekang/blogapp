package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public String login(@Valid LoginReqDto dto, BindingResult bindingResult , Model model) {
		
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드:" + error.getField());
				System.out.println("메시지:" + error.getDefaultMessage());
			}
			model.addAttribute("errorMap",errorMap);
			return "error/error";
		}
		
		
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
	public String join(@Valid JoinReqDto dto, BindingResult bindingResult , Model model) {
		
		System.out.println("에러사이즈" + bindingResult.getFieldErrors().size());
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드:" + error.getField());
				System.out.println("메시지:" + error.getDefaultMessage());
			}
			model.addAttribute("errorMap",errorMap);
			return "error/error";
		}
		
		userRepository.save(dto.toEntity());
		return "redirect:/loginForm";
	}
	
}
