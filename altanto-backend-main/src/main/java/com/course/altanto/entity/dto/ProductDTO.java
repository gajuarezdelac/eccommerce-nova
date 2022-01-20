package com.course.altanto.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDTO {
	
	private String id;
	
	private String code;
	
	private String name;

	private String shortDescription;
	
	private String longDescription;	

	private int cantd;
	
	private double price;
	
	private Date createAt;

	private int discount;
	
	private String category;

	private double rating;
	
	private String typeGarment;
	
}
