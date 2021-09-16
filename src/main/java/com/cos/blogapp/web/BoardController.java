package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙은 field에 대한 constructor 를 생성한다 -> 생성자 주입 DI
@Controller
public class BoardController {
		// DI
		private final BoardRepository boardRepository;
		private final HttpSession session;

		/*   
		  1. Controller 선정 
		  2. http Method 선정
		  3. 받을 data 확인 ( body, Query String, Path Variable )
		  Query String, Path Variable -> DB 의 Where 문
		  4. DB에 접근 해야하면 Model에 접근 or Else Model에 접근 x
		 */
		
		
		@GetMapping("/board/{id}")
		public String detail(@PathVariable int id, Model model) {
			// select * from board where id = :id
			
			// orElse는 값을 찾으면 Board가 return 못 찾으면 (괄호안 내용 return)
			// *자주 쓰지는 않음
			// orElseThrow
			// Board boardEntity = boardRepository.findById(id)
			//		.orElse( new Board( 100, "글없어요", "내용없어요" , null);
			
			
			Board boardEntity = boardRepository.findById(id)
					.orElseThrow(new Supplier<MyNotFoundException>() {

						@Override
						public MyNotFoundException get() {
							return new MyNotFoundException(id + "를 찾을 수 없습니다" );
						}
					});
			
			
			
			
			model.addAttribute("boardEntity",boardEntity);
			return "board/detail";
		}
		
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
			
			// 공통기능 시작
			// 인증체크
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
			// 공통기능 끝 login 관리
			
			//dto.setContent(dto.getContent().replaceAll("<p>", ""));
			//dto.setContent(dto.getContent().replaceAll("</p>", ""));
			
			String html = dto.toEntity(principal).toString();
			
			System.out.println( html);
			

			boardRepository.save(dto.toEntity(principal));
			
			
			 
			
			return Script.href("/","글쓰기 성공");
		}
		
}
