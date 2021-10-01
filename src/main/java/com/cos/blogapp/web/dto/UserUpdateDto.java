package com.cos.blogapp.web.dto;

import javax.validation.constraints.NotBlank; 
import javax.validation.constraints.Size;

import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
	

	
	@Size(min = 2,max = 50)
	@NotBlank
	private String email;
	
	public User toEntity() {
		User user = new User();
		user.setEmail(email);
		
		
		return user;
	}

	
}
