package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IOrdersService {
	
	Orders createOrder(Orders request);
	
	Orders updateOrder(int status, String id);
	
	Orders deleteOrder(String id) throws ExceptionGeneric;
	
	List<Orders> viewOrderByUser(String userId);
	
	Orders viewOrderById(String id) throws ExceptionGeneric;
	
	List<Orders> getAllOrders();
		
}
