package com.course.altanto.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Inbox;

@Repository
public interface IInboxRepository extends JpaRepository<Inbox, String> {
	
	
	Inbox findInboxById(String id);
	
}
