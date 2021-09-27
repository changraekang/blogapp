package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.handler.ex.MyAPINotFoundException;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.CMRespDto;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final UserRepository userRepository;
	private final HttpSession session;

	@GetMapping("/logout")
	public String logout() {

		session.invalidate();
		return "redirect:/"; // 게시글 목록화면에 data 가 없다 (model에서 data를 안가져왔기 때문)
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}

	// 로그인
	@PostMapping("/login")
	public @ResponseBody String login(@Valid LoginReqDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}

		User userEntity = userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
		if (userEntity == null) { // username, password 잘못 기입
			return Script.back("아이디 혹은 비밀번호를 잘못 입력하였습니다.");
		} else {
			// 세션 날라가는 조건 : 1. session.invalidate(), 2. 브라우저를 닫으면 날라감
			session.setAttribute("principal", userEntity);
			return Script.href("/", "로그인 성공");
		}
	}

	// 회원가입
	@PostMapping("/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) {

		// 1.유효성 검사 실패 - Java Script response(alert->back)
		// 2.정상 - login page

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드:" + error.getField());
				System.out.println("메시지:" + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);

		dto.setPassword(encPassword);
		userRepository.save(dto.toEntity());
		return Script.href("/loginForm"); // 리다이렉션 (300)
	}

	// 회원정보 확인
	@GetMapping("/user/{id}")
	public String userinfo(@PathVariable int id) {
		// 기본은 userRepository.findById(id) -> DB에서 가져와야 함
		// 우회적으로 session value 를 가져올 수 있다
		// Validation 체크 불필요 자신의 session 만 가져오기 때문

		return "user/updateForm";
	}

	// 회원정보 수정
	@PutMapping("user/{id}")
	public @ResponseBody CMRespDto<String> userUpdate(@PathVariable int id, @RequestBody JoinReqDto dto,
			BindingResult bindingResult) {
		// 인증이 된 사람만 함수 접근 가능!! (로그인 된 사람)
		User principal = (User) session.getAttribute("principal");
		if (principal == null) {
			throw new MyAPINotFoundException("인증이 되지 않습니다");
		}
		
		User userEntity = userRepository.findById(id)
				.orElseThrow(()-> new MyAPINotFoundException("해당회원을 찾을 수 없습니다"));
		
		if (principal.getId() != userEntity.getId()) {
			throw new MyAPINotFoundException("해당글을 수정할 권한이 없습니다.");
		}
		
		System.out.println("원래" + principal.getPassword());
		System.out.println("입력dto" +dto.getPassword() );
		System.out.println("입력user" + userEntity.getPassword());
		
		try {
			User user = dto.toEntity(principal);
			user.setId(id);
			
			if (principal.getPassword() != dto.getPassword() ) {
				
				String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
				user.setPassword(encPassword);
			}
			user.setPassword(dto.getPassword());
			System.out.println(user.toString());
			userRepository.save(user);

		} catch (Exception e) {
			// TODO: handle exception
		}

		
		
		return new CMRespDto<>(1, "업데이트 성공", null);
	}

}
