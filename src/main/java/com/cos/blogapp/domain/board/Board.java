package com.cos.blogapp.domain.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String 	title;
	private String 	content;

}
