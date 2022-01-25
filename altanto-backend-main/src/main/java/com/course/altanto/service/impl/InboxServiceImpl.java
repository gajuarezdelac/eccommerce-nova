package com.course.altanto.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Inbox;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IInboxRepository;
import com.course.altanto.service.IInboxService;

@Component
@Transactional
public class InboxServiceImpl implements IInboxService{
	
	public IInboxRepository inboxRepository;
	
	public InboxServiceImpl(IInboxRepository inboxRepository) {
		this.inboxRepository = inboxRepository;
	}
	
	@Override
	public Inbox contact(String email, String subject, String content) {
	
		Inbox element = new Inbox();
		element.setEmail(email);
	    element.setContent(content);
	    element.setSubject(subject);
		element.setCreatedAt(new Date());
		inboxRepository.save(element);
		
		return element;
	}

	@Override
	public List<Inbox> viewAllInbox() {
		return inboxRepository.findAll();
	}

	@Override
	public Inbox viewInboxById(String id) throws ExceptionGeneric {
		Inbox response = validateInboxExist(id);
		return response;
	}

	@Override
	public Inbox deleteInboxById(String id) throws ExceptionGeneric {
		Inbox response = validateInboxExist(id);
		inboxRepository.deleteById(id);
		return response;
	}
	
	private Inbox validateInboxExist(String id) throws ExceptionGeneric {
		
		Inbox element = inboxRepository.findInboxById(id);
		if(element == null ) {
			 throw new ExceptionGeneric("No existe el mensaje");
		}
		return element;
	}

	@Override
	public Page<Inbox> search(int pageNo, int pageSize, String subject, String content, String email) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Inbox> response = inboxRepository.searchIboxByFilters(subject, content, email, pageable);	  
		return response;
	}
	

}
