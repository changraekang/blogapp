package com.cos.blogapp.web;

import java.security.Principal;
import java.util.HashMap; 
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 field에 대한 constructor 를 생성한다
@Controller
public class BoardController {

		private final BoardRepository boardRepository;
		private final HttpSession session;

		
		
		@GetMapping("/board/saveForm")
		public String saveForm() {
			
			return"board/saveForm";
		}
		
		@GetMapping("/board")
		public String home ( Model model, int page) {
			
			PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
			
			Page<Board> boardsEntity =
					boardRepository.findAll(pageRequest);
			
			model.addAttribute("boardsEntity", boardsEntity);
			
			return "board/list";
			
		}
	

		@PostMapping("/board")
		public @ResponseBody String save(@Valid BoardSaveReqDto dto , BindingResult bindingResult) {
			User principal = (User) session.getAttribute("principal");
			
			//인증체크
			if (principal == null) { // login 안됨
				return Script.href("/loginForm","잘못된 접근입니다.");
			}
			
			if(bindingResult.hasErrors()) {
				Map<String, String> errorMap = new HashMap<>();
				for(FieldError error : bindingResult.getFieldErrors()) {
					errorMap.put(error.getField(), error.getDefaultMessage());
					System.out.println("필드:" + error.getField());
					System.out.println("메시지:" + error.getDefaultMessage());
				}
				return Script.back(errorMap.toString());
			}
			boardRepository.save(dto.toEntity(principal));
			
			
			 
			
			return Script.href("/","글쓰기 성공");
		}
		
}
