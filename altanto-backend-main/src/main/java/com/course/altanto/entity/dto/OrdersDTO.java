package com.course.altanto.entity.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrdersDTO {

	
	private String userId;

	private double subtotal;
	
	private double discount;
	
	private double total;
	
	private String typeSend;
	
	private String reference;
	
	private String statusReference;
	
	private String methodPayment;

	
	
}
