package com.course.altanto.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Review;
import com.course.altanto.entity.dto.ReviewDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IReviewRepository;
import com.course.altanto.service.IReviewService;

@Component
@Transactional
public class ReviewServiceImpl implements IReviewService{
	
	
	private IReviewRepository reviewRepository;
	
	public ReviewServiceImpl(IReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Override
	public List<Review> getAllReviews() {
		return reviewRepository.findAll();
	}

	@Override
	public List<Review> getReviewsByProduct(String codeProd) {
		return reviewRepository.findReviewByCodeProd(codeProd);
	}

	@Override
	public Review viewReviewById(String id) throws ExceptionGeneric {
		Review response = validateReviewExist(id);
		return response;
	}

	@Override
	public Review addReview(ReviewDTO request) {

		Review element  = new Review();
		element.setCalf(request.getCalf());
		element.setCodeProd(request.getCodeProd());
		element.setMessage(request.getMessage());
		element.setUserId(request.getUserId());
		element.setCreatedAt(new Date());
		reviewRepository.save(element);
		
		return element;
	}

	@Override
	public Review updateReview(String id, ReviewDTO request) throws ExceptionGeneric {
		
		Review element = validateReviewExist(id);
		element.setCalf(request.getCalf());
		element.setMessage(request.getMessage());
		element.setCreatedAt(new Date());
		reviewRepository.save(element);
		
		return element;
	}
	

	@Override
	public Review deleteReview(String id) throws ExceptionGeneric {
		Review element = validateReviewExist(id);
		reviewRepository.deleteById(element.getId());
		return element;
	}
	
	
	private Review validateReviewExist(String id) throws ExceptionGeneric {
		
		Review element = reviewRepository.findReviewById(id);
		
		if(element == null ) {
			 throw new ExceptionGeneric("No se encontro el review");
		}
		return element;
	}

	@Override
	public Page<Review> getAllReviewsPaginate(int pageNo, int pageSize, String codeProd, String message, String userId) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);   
		Page<Review> response = reviewRepository.searchByFilters(codeProd, message, userId, pageable);		  
		return response;
	}
	
	


}
