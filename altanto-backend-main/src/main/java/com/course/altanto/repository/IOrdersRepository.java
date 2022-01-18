package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Orders;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, String> {
	
	Orders findOrdersById(String id);
	
	List<Orders> findOrdersByUserId(String userId);
		
}

	