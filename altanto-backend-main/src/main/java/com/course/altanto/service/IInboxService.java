package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Inbox;

@Component
public interface IInboxService {
	
	 Inbox contact(String email, String subject, String content);
	 
	 List<Inbox> viewAllInbox();
	 
	 Inbox viewInboxById(String id);
	 
	 Inbox deleteInboxById(String id);
	 
}
