package com.cos.blogapp.web.dto;


import com.cos.blogapp.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
	private int code; // 1성공 -1실패 (enum)
	private String msg;
	private T body;
}
