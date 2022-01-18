package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Review;

@Component
public interface IReviewService {
	
	List<Review> getAllReviews();
	
	List<Review> getReviewsByProduct();
	
	Review viewReviewById();
	
	Review addReview();
	
	Review updateReview();
	
	void deleteReview();	
	
	
	
}
