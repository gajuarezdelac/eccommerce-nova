package com.course.altanto.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {
	
	private String names;
	
	private String surnames;
	
	private String username;
	
	private String gender;
	
	private String numberPhone;
	
	private Date dateOfBirth;
	
	private String location;
	
}
