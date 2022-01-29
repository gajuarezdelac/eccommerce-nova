package com.course.altanto.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewDTO {
	
	private String id;

	private String codeProd;
	
	private String userId;
	
	private int calf;
	
	private String message;
	
	private Date createAt;
}
