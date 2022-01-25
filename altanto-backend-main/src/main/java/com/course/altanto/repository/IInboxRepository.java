package com.course.altanto.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Inbox;

@Repository
public interface IInboxRepository extends JpaRepository<Inbox, String> {
	
	Inbox findInboxById(String id);
	
	@Query(value = "SELECT * FROM inbox i WHERE i.subject LIKE %:subject% AND i.content LIKE %:content% AND i.email LIKE %:email%",nativeQuery = true)
	Page<Inbox> searchIboxByFilters(@Param("subject") String subject,@Param("content") String content,@Param("email") String email, Pageable pageable);
	
}
