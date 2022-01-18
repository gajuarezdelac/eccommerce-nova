package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Inbox;
import com.course.altanto.service.IInboxService;

@RestController
@RequestMapping("/inbox")
public class InboxController {

	private IInboxService service;
	
	private InboxController(IInboxService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
		public ResponseEntity<Inbox> test(@PathVariable(value = "id") String id) {
		Inbox response = service.viewInboxById(id);
			 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/list")
	    public ResponseEntity<List<Inbox>> list(){
	    	List<Inbox> list = service.viewAllInbox();
	    	return new ResponseEntity<>(list, HttpStatus.OK);
	}
	    
	
	
	
	
	
	
}
