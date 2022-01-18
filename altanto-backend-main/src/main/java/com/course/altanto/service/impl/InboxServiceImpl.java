package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Inbox;
import com.course.altanto.service.IInboxService;

@Component
@Transactional
public class InboxServiceImpl implements IInboxService{

	@Override
	public Inbox contact(String email, String subject, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Inbox> viewAllInbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inbox viewInboxById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inbox deleteInboxById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
