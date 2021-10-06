package com.cos.blogapp.domain.board;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.jpa.repository.Query;

import com.cos.blogapp.domain.comment.Comment;
import com.cos.blogapp.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 	id;
	@Column(nullable = false, length = 50)
	private String 	title;
	@Lob
	private String 	content;
	
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER) 
	private User user;
	
	
	// 양방향 Mapping 
	@JsonIgnoreProperties({"board"})
	@OneToMany(mappedBy = "board",fetch = FetchType.LAZY)
	private List<Comment> comments;



	
	
}
