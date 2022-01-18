package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;

@Component
public interface IOrdersService {
	
	Orders createOrder(Orders request);
	
	Orders updateOrder(Orders request);
	
	Orders deleteOrder();
	
	List<Orders> viewOrderByUser(String userId);
	
	List<Orders> getAllOrders();
		
}
