package com.cos.blogapp.service;

import java.util.HashMap; 
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAPINotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.web.dto.BoardSaveReqDto;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	// 생성자 주입(DI)
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;

	

	@Transactional
	public void 게시글수정(int id, User principal, BoardSaveReqDto dto) {

		// 권한이 있는 사람만 함수 접근 가능(principal.id == {id})
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyAPINotFoundException("해당글을 찾을 수 없습니다"));
		if (principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAPINotFoundException("해당글을 수정할 권한이 없습니다.");
		}

		try {

			Board board = dto.toEntity(principal);
			board.setId(id);
			boardRepository.save(board);
		} catch (Exception e) {
			throw new MyAPINotFoundException(id + "를 찾을 수 없어 수정할 수 없습니다");
		}

	} // @Transactional 종료

	public Board 게시글수정이동(int id) {
		// 게시글정보가져오기
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyNotFoundException(id + "번의 게시글을 찾을 수 없습니다."));

		return boardEntity;
	}
	// Transactional = 트랜잭션 시작
	// rollbackFor ( 함수내부에 하나의 write라도 실패하면 전체를 rollback 하는 것)
	// 주의 RuntimeException을 던져야 동작한다
	@Transactional(rollbackFor = MyAPINotFoundException.class)
	public void 게시글삭제(int id, User principal) {
		// 권한이 있는 사람만 함수 접근 가능(principal.id == {id})
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(() -> new MyAPINotFoundException("해당글을 찾을 수 없습니다"));

		if (principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAPINotFoundException("해당글을 삭제할 권한이 없습니다.");
		}

		try {

			boardRepository.deleteById(id); // error -> id가 없으면
		} catch (Exception e) {
			throw new MyAPINotFoundException(id + "번 게시글을 찾을 수 없어 삭제할 수 없습니다");
		}
	}
	

	public Board 게시글상세보기이동(int id) {
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> new MyNotFoundException(id + "를 못 찾았어요"));
		return boardEntity;
	}
	
	
	@Transactional
	public void 게시글등록(BoardSaveReqDto dto , User principal) {

		boardRepository.save(dto.toEntity(principal));

	}

	public Page<Board> 게시글목록조회(int page) {
		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));

		Page<Board> boardsEntity = boardRepository.findAll(pageRequest);
		System.out.println(boardsEntity + "보즈엔티티");
		return boardsEntity;
	}
	public Page<Comment> 댓글목록조회(int page) {
		PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
		
		Page<Comment> commentsEntity = (Page<Comment>) commentRepository.findAll();
		
		return commentsEntity;
	}

}
