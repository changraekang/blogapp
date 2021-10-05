package com.cos.blogapp.web.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveReqDto {
	
	
	@NotBlank
	@Size(min = 1, max = 255)
	private String content;
	 
	public Comment toEntity( User principal, Board boardEntity ) {
		
		Comment comment = new Comment();
		System.out.println("댓글" + content);
		comment.setContent(content);
		comment.setUser(principal);
		comment.setBoard(boardEntity);
		
		return comment;
	}
	
	

}
