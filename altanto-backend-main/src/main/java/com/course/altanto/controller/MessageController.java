package com.course.altanto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.course.altanto.entity.dto.Message;

@Controller
public class MessageController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * Returns  message for a chatroom.
	 * 
	 * @param Message   - Model (ReceivedName, Message, etc.)
	 * @return - Model of message
	*/
	
	
	@MessageMapping("/message")
	@SendTo("/chatroom/public")
	public Message receivePublicMessage(@Payload Message message) {
		return message;
	}

	/**
	 * Returns  message for a chatroom.
	 * 
	 * @param Message   - Model (ReceivedName, Message, etc.)
	 * @return - Model of message
	 */
	@MessageMapping("/private-message")
	public Message receivePrivateMessage(@Payload Message message) {
		simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
		return message;
	}

	
	
}
