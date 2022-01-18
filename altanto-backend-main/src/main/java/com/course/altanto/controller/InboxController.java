package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Inbox;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IInboxService;

@RestController
@RequestMapping("/inbox")
public class InboxController {

	private IInboxService service;
	
	private InboxController(IInboxService service) {
		this.service = service;
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<Inbox> test(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Inbox response = service.viewInboxById(id);
			 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@GetMapping("/list")
	public ResponseEntity<List<Inbox>> list(){
	    	List<Inbox> list = service.viewAllInbox();
	    	return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/contact")
	public ResponseEntity<Inbox> contact(@RequestBody Inbox content){
		Inbox list = service.contact(content.getEmail(), content.getSubject(), content.getContent());
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Inbox> contact(@PathVariable String id) throws ExceptionGeneric{
		Inbox list = service.deleteInboxById(id);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
}
