package com.cos.blogapp.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cos.blogapp.domain.board.Board;
import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int		id;			//PK Primary Key (자동증가번호)
	
	@Column(nullable = false, length = 50)
	private String content;
	
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER) 
	private User user;
	
	@JoinColumn(name = "boardId")
	@ManyToOne(fetch = FetchType.EAGER) 
	private Board board;

	
	
	
	
}
