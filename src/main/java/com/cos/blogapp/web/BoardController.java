package com.cos.blogapp.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAPINotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.service.BoardService;
import com.cos.blogapp.util.Script;
import com.cos.blogapp.web.dto.BoardSaveReqDto;
import com.cos.blogapp.web.dto.CMRespDto;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor // final이 붙은 field에 대한 constructor 를 생성한다 -> 생성자 주입 DI
@Controller
public class BoardController {
	// DI
	private final BoardService boardService;
	private final HttpSession session;

	// 댓글
	@PostMapping("/board/{boardid}/comment")
	public String commentSave(@PathVariable int boardid, @Valid CommentSaveReqDto dto) {
		User principal = (User) session.getAttribute("principal");

		boardService.댓글등록(boardid, dto, principal);

		return "redirect:/board/" + boardid;
	}



	// 글 수정
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @RequestBody BoardSaveReqDto dto,
			BindingResult bindingResult) {
		// 인증이 된 사람만 함수 접근 가능!! (로그인 된 사람)
		
		// 공통로직 시작
		User principal = (User) session.getAttribute("principal");
		if (principal == null) {
			throw new MyAPINotFoundException("인증이 되지 않습니다");
		}
		// 유효성 검사
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAPINotFoundException(errorMap.toString());
		}
		// 공통로직 끝
		
		
		
		boardService.게시글수정(id, principal, dto);
		
		
		return new CMRespDto<>(1, "업데이트 성공", null);

	}

	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		
		 
		model.addAttribute("boardEntity", boardService.게시글수정이동(id));
		return "board/updateForm";
	}

	// API(AJAX) 요청
	@DeleteMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {

		// 인증이 된 사람만 함수 접근 가능!! (로그인 된 사람)
		User principal = (User) session.getAttribute("principal");
		if (principal == null) {
			throw new MyAPINotFoundException("인증이 되지 않습니다");
		}

		boardService.게시글삭제(id, principal);

		return new CMRespDto<String>(1, "성공", null);
	}

	// RestFul API 주소설계방식
	/*
	 * 1. Controller 선정 2. http Method 선정 3. 받을 data 확인 ( body, Query String, Path
	 * Variable ) Query String, Path Variable -> DB 의 Where 문 4. DB에 접근 해야하면 Model에
	 * 접근 or Else Model에 접근 x
	 */

	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		// select * from board where id = :id

		// orElse는 값을 찾으면 Board가 return 못 찾으면 (괄호안 내용 return)
		// *자주 쓰지는 않음
		// orElseThrow
		// Board boardEntity = boardRepository.findById(id)
		// .orElse( new Board( 100, "글없어요", "내용없어요" , null);

	
		// Comment commentsEntity = commentRepository.getComment(id);
		// model.addAttribute("commentsEntity",commentsEntity);
		
		model.addAttribute("boardEntity",  boardService.게시글상세보기이동(id));
		return "board/detail";
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {

		return "board/saveForm";
	}

	@GetMapping("/board")
	public String home(Model model, int page) {

	
		model.addAttribute("boardsEntity",  boardService.게시글목록조회(page));
		return "board/list";

	}

	@PostMapping("/board")
	public @ResponseBody String save(@Valid BoardSaveReqDto dto, BindingResult bindingResult) {
		User principal = (User) session.getAttribute("principal");

		// 공통기능 시작
		// 인증체크
		if (principal == null) { // login 안됨
			return Script.href("/loginForm", "잘못된 접근입니다.");
		}

		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드:" + error.getField());
				System.out.println("메시지:" + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		// 공통기능 끝 login 관리
		// dto.setContent(dto.getContent().replaceAll("<p>", ""));
		// dto.setContent(dto.getContent().replaceAll("</p>", ""));
		
		boardService.게시글등록(dto, principal);
		return Script.href("/", "글쓰기 성공");
	}

}
