package com.course.altanto.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;
import com.course.altanto.entity.dto.CreateOrderDTO;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IOrdersService {
	
	Orders createOrder(CreateOrderDTO request) throws MessagingException;
	
	Orders updateOrder(int status, String id);
	
	Orders deleteOrder(String id) throws ExceptionGeneric;
	
	List<Orders> viewOrderByUser(String userId);
	
	Orders viewOrderById(String id) throws ExceptionGeneric;
	
	List<Orders> getAllOrders();
	
	Page<Orders> searchOrders(int pageNo, int pageSize, String id, String userId, String dateBegin, String dateFinish);
		
}
