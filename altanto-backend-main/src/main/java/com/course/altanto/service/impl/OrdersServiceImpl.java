package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;
import com.course.altanto.service.IOrdersService;

@Component
@Transactional
public class OrdersServiceImpl implements IOrdersService{

	@Override
	public Orders createOrder(Orders request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders updateOrder(Orders request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Orders deleteOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> viewOrderByUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Orders> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
