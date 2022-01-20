package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, String> {
	
	Review findReviewById(String id);
	
    List<Review> findReviewByCodeProd(String codeProd);
    
    
}
