package com.cos.blogapp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CollectionId;

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
	@Column(nullable = false, length = 60, unique = true)
	private String 	username; 	// 아이디
	@Column(nullable = false, length = 70)
	private String	password;
	@Column(nullable = false, length = 50)
	private String 	email;


}
