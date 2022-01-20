package com.course.altanto.entity.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

	private String codeProd;
	
	private String userId;
	
	private int calf;
	
	private String message;
	
	private Date createAt;
}
