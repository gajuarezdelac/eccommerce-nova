package com.course.altanto.entity.dto;

import lombok.Data;

@Data
public class RecoveryPasswordDTO {

	private String oldPassword;
	
	private String newPassword;
	
	private String email;
	
	private String token;
	
}
