package com.course.altanto.entity.dto;

import com.course.altanto.enumeration.Status;

import lombok.Data;

@Data
public class Message {

	private String senderName;
	
	private String receiverName;
	
	private String message;
	
	private String date;
	
	private Status status;
	

}
