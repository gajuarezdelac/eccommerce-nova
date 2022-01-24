package com.course.altanto.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IOrdersRepository;
import com.course.altanto.service.IOrdersService;

@Component
@Transactional
public class OrdersServiceImpl implements IOrdersService{

	private IOrdersRepository ordersRepository;
	
	public OrdersServiceImpl(IOrdersRepository ordersRepository) {
		this.ordersRepository = ordersRepository;
	}
	
	@Override
	public Orders createOrder(Orders request) {
		Orders element = new Orders();
		element.setCodeProd(request.getCodeProd());
		element.setAmount(request.getAmount());
		element.setUserId(request.getUserId());
		element.setStatus(request.getStatus());
		element.setListProducts(request.getListProducts());
		element.setCreateAt(new Date());
		ordersRepository.save(element);
		return element;
	}

	@Override
	public Orders updateOrder(int status, String id) {
		Orders response = ordersRepository.findOrdersById(id);
		response.setStatus(status);
		return response;
	}

	@Override
	public Orders deleteOrder(String id) throws ExceptionGeneric {
		Orders element = validateOrdersExist(id);
		ordersRepository.deleteById(element.getId());
		return element;
	}

	@Override
	public List<Orders> viewOrderByUser(String userId) {
		List<Orders> list = ordersRepository.findOrdersByUserId(userId);
		return list;
	}

	@Override
	public Orders viewOrderById(String id) throws ExceptionGeneric {
		Orders response = validateOrdersExist(id);
		return response;
	}

	@Override
	public List<Orders> getAllOrders() {
		return ordersRepository.findAll();
	}
	
	
	private Orders validateOrdersExist(String id) throws ExceptionGeneric {
		Orders element = ordersRepository.findOrdersById(id);
		if(element == null ) {
			 throw new ExceptionGeneric("No existe la orden");
		}
		return element;
	}

	@Override
	public Page<Orders> searchOrders(int pageNo, int pageSize) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Orders> response = ordersRepository.findAll(pageable);		  
		return response;
	}
	
}
