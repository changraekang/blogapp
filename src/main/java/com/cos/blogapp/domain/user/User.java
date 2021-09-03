package com.cos.blogapp.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//테이블모델
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int		id;			//PK Primary Key (자동증가번호)
	private String 	username; 	// 아이디
	private String	password;
	private String 	email;


}
