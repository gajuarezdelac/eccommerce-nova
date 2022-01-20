package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Review;
import com.course.altanto.entity.dto.ReviewDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
	private IReviewService service;
	
	public ReviewController(IReviewService service) {
		this.service = service;
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<Review>> getAllReviews() {
		List<Review> response = service.getAllReviews();
		return new ResponseEntity<List<Review>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Review response = service.viewReviewById(id);
		return new ResponseEntity<Review>(response, HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable("id") String id) {
		List<Review> response = service.getReviewsByProduct(id);
		return new ResponseEntity<List<Review>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Review> addReview(@RequestBody ReviewDTO request) {
		Review response =  service.addReview(request);
		return new ResponseEntity<Review>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<Review> deleteReview(@PathVariable("id") String id) throws ExceptionGeneric {
		Review response = service.deleteReview(id);
		return new ResponseEntity<Review>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") String id, @RequestBody ReviewDTO request) throws ExceptionGeneric {
		Review response = service.updateReview(id, request);
		return new ResponseEntity<Review>(response,HttpStatus.OK );
	}
	
}
