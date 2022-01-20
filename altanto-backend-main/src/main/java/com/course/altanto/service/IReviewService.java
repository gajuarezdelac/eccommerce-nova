package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Review;
import com.course.altanto.entity.dto.ReviewDTO;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IReviewService {
	
	List<Review> getAllReviews();
	
	List<Review> getReviewsByProduct(String codeProd);
	
	Review viewReviewById(String id) throws ExceptionGeneric;
	
	Review addReview(ReviewDTO request);
	
	Review updateReview(String id, ReviewDTO request) throws ExceptionGeneric;
	
	Review deleteReview(String id) throws ExceptionGeneric;	
	
}
