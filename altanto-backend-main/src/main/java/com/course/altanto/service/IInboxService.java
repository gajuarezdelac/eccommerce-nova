package com.course.altanto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Inbox;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IInboxService {
	
	 Inbox contact(String email, String subject, String content);
	 
	 List<Inbox> viewAllInbox();
	 
	 Inbox viewInboxById(String id) throws ExceptionGeneric;
	 
	 Inbox deleteInboxById(String id) throws ExceptionGeneric;
	 
	 Page<Inbox> search(int pageNo, int pageSize);
	 
}
