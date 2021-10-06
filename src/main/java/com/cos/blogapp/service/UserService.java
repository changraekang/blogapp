package com.cos.blogapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.domain.user.UserRepository;
import com.cos.blogapp.handler.ex.MyAPINotFoundException;
import com.cos.blogapp.util.MyAlgorithm;
import com.cos.blogapp.util.SHA;
import com.cos.blogapp.web.dto.JoinReqDto;
import com.cos.blogapp.web.dto.LoginReqDto;
import com.cos.blogapp.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	
	//생성자 주입(DI)
	
	private final UserRepository userRepository;
	
	@Transactional
	public void 회원가입(JoinReqDto dto) {
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);

		dto.setPassword(encPassword);
		userRepository.save(dto.toEntity());
	}
	
	public  User 로그인(LoginReqDto dto) {
	 User userEntity =	userRepository.mLogin(dto.getUsername(), SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256));
			return userEntity;
	}
	
	@Transactional
	public void 회원정보수정(User principal, int id , UserUpdateDto dto ) {
		User userEntity = userRepository.findById(id)
				.orElseThrow(()-> new MyAPINotFoundException("해당회원을 찾을 수 없습니다"));
		
		if (principal.getId() != userEntity.getId()) {
			throw new MyAPINotFoundException("해당글을 수정할 권한이 없습니다.");
		}
		
		// 핵심로직		
		principal.setEmail(dto.getEmail());
		userRepository.save(principal);
	}
}

