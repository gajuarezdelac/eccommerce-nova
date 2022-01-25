package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.course.altanto.entity.Review;

@Repository
public interface IReviewRepository extends JpaRepository<Review, String> {
	
	Review findReviewById(String id);
	
    List<Review> findReviewByCodeProd(String codeProd);
    
	@Query(value = "SELECT * FROM review r WHERE r.code_prod LIKE %:code_prod% AND r.message LIKE %:message% AND r.user_id LIKE %:user_id%",nativeQuery = true)
	Page<Review> searchByFilters(@Param("code_prod") String codeProd,@Param("message") String message,@Param("user_id") String userId, Pageable pageable);
	
	
  
}
