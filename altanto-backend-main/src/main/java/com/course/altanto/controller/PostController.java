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

import com.course.altanto.entity.Post;
import com.course.altanto.entity.dto.PostDTO;
import com.course.altanto.service.IPostService;

@RestController
@RequestMapping("/post")
public class PostController {

	
	private IPostService service;

    private PostController(IPostService service) { 
    	this.service = service;
    }
	
    @GetMapping("/{id}")
	public ResponseEntity<Post> test(@PathVariable(value = "id") long id) {
    	Post response = service.getPostById(id);
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @GetMapping("/list")
    public ResponseEntity<List<Post>> list(){
    	List<Post> list = service.getPosts();
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Post> add(@RequestBody PostDTO param) {
    	Post response  =  service.createPost(param);
    	return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/update")
    public ResponseEntity<Post> update(@RequestBody PostDTO param) {
    	Post response  =  service.updatePost(param);
    	return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") long id) { 
    	 service.deletePost(id);
		 return new ResponseEntity<>(HttpStatus.OK);
	}
    
    
}
