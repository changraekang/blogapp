package com.cos.blogapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.comment.CommentRepository;
import com.cos.blogapp.domain.user.User;
import com.cos.blogapp.handler.ex.MyAPINotFoundException;
import com.cos.blogapp.handler.ex.MyNotFoundException;
import com.cos.blogapp.web.dto.CommentSaveReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	
	@Transactional(rollbackFor = MyNotFoundException.class)
	public void 댓글등록(int boardid, CommentSaveReqDto dto, User principal) {
		
		Board boardEntity = boardRepository.findById(boardid)
				.orElseThrow(() -> new MyNotFoundException(boardid + "번의 게시글을 찾을 수 없습니다."));
		
		commentRepository.save(dto.toEntity(principal, boardEntity));
		
	} // @Transactional 종료
	
	@Transactional(rollbackFor = MyAPINotFoundException.class)
	public void 댓글삭제 (int id, User principal) {
		// 권한이 있는 사람만 함수 접근 가능(principal.id == {id})
		System.out.println(principal);
		Comment commentEntity = commentRepository.findById(id)
				.orElseThrow(() -> new MyAPINotFoundException("해당 댓글을 찾을 수 없습니다"));
		
		if (principal.getId() != commentEntity.getUser().getId()) {
			throw new MyAPINotFoundException("해당글을 삭제할 권한이 없습니다.");
		}
		
		try {
			
			commentRepository.deleteById(id); // error -> id가 없으면
		} catch (Exception e) {
			throw new MyAPINotFoundException(id + "번 게시글을 찾을 수 없어 삭제할 수 없습니다");
		}
	}
	
	
	
}
