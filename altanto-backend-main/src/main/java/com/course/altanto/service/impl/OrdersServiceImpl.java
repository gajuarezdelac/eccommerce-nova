package com.course.altanto.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Orders;
import com.course.altanto.entity.Product;
import com.course.altanto.entity.dto.CreateOrderDTO;
import com.course.altanto.entity.dto.ProductsCantdDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IOrdersRepository;
import com.course.altanto.repository.IProductRepository;
import com.course.altanto.repository.IUserRepository;
import com.course.altanto.service.IOrdersService;

@Component
@Transactional
public class OrdersServiceImpl implements IOrdersService{

	private IOrdersRepository ordersRepository;
	private IProductRepository productRepository;
	private IUserRepository userRepository;
	
	public OrdersServiceImpl(IOrdersRepository ordersRepository, IProductRepository productRepository, IUserRepository userRepository) {
		this.ordersRepository = ordersRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}


	@Override
	public Orders createOrder(CreateOrderDTO request) {
		
		// Almacenar los datos primarios de la orden
		Orders element = new Orders();
		
		updateQuantity(request.getList());
//		element.setAmount(request.getAmount());
//		element.setUserId(request.getUserId());
//		element.setStatus(request.getStatus());
//		element.setAddressId(request.getAddressId());
//		element.setListProducts(request.getListProducts());
		
		
		element.setCreatedAt(new Date());
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
	public Page<Orders> searchOrders(int pageNo, int pageSize,String id, String userId, String dateBegin, String dateFinish) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Orders> response = ordersRepository.searchByFilters(id, userId, dateBegin, dateFinish, pageable);  
		return response;
	}
	
	
	
	private void updateQuantity(List<ProductsCantdDTO> listIds) {
		
		
		
		for(ProductsCantdDTO e :  listIds) {
			Product product = productRepository.findProductByCodeAndSizeAndId(e.getCode(), e.getSize(), e.getSize());
			// Calculate cantd
			int cantCurrently = product.getCantd();
			System.out.println(cantCurrently);
			int newCantd = cantCurrently - e.getCantd();
			System.out.println(newCantd);
			product.setCantd(newCantd);
			productRepository.save(product);
		}
	}
	
	
	
	
}
