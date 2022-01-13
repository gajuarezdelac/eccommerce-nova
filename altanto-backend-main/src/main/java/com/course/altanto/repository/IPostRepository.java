package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
	
	
}


