package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Orders;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, String> {
	
	Orders findOrdersById(String id);
	
	List<Orders> findOrdersByUserId(String userId);
	
	@Query(value = "SELECT * FROM orders o WHERE o.id LIKE %:id% AND o.user_id LIKE %:user_id%  AND o.created_at >= :dateBegin AND o.created_at <= :dateFinish",nativeQuery = true)
	Page<Orders> searchByFilters(@Param("id") String id,@Param("user_id") String userId,@Param("dateBegin")  String dateBegin, @Param("dateFinish") String dateFinish , Pageable pageable);
	
	
	
}

	