package com.course.altanto.entity.dto;

import lombok.Data;

@Data
public class PostDTO {

	private Long id;
	
	private String title;
	
	private String description;
	
	private String content;
	
	private String category;
	
	private String dateCreation;
	
	private String urlImage;
	
}
