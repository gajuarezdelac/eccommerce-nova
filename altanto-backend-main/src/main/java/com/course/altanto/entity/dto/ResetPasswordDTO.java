package com.course.altanto.entity.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

	private String email;
	
	private String token;
	
	private String password;
	
	
}
