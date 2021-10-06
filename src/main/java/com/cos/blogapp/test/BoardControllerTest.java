package com.cos.blogapp.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardControllerTest {

	private final BoardRepository boardRepository;
	
	@GetMapping("/test/board/{id}")
	public Board detail ( @PathVariable int id ) {
		Board board = boardRepository.findById(id).get();
		System.out.println(board);
		return board;
	}
	
}
