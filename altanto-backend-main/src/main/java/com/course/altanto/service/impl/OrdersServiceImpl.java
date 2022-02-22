package com.course.altanto.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Address;
import com.course.altanto.entity.Orders;
import com.course.altanto.entity.Product;
import com.course.altanto.entity.ProductByOrder;
import com.course.altanto.entity.User;
import com.course.altanto.entity.dto.AddressDTO;
import com.course.altanto.entity.dto.CreateOrderDTO;
import com.course.altanto.entity.dto.ProductsCantdDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IAddressRepository;
import com.course.altanto.repository.IOrdersRepository;
import com.course.altanto.repository.IProductByOrderRepository;
import com.course.altanto.repository.IProductRepository;
import com.course.altanto.repository.IUserRepository;
import com.course.altanto.service.IOrdersService;

@Component
@Transactional
public class OrdersServiceImpl implements IOrdersService{

	private IOrdersRepository ordersRepository;
	private IProductRepository productRepository;
	private IUserRepository userRepository;
	private IAddressRepository addressRepository;
	private IProductByOrderRepository productByOrderRepository;
	
	
	public OrdersServiceImpl(IOrdersRepository ordersRepository, IProductRepository productRepository, IUserRepository userRepository,IAddressRepository addressRepository,IProductByOrderRepository productByOrderRepository) {
		this.ordersRepository = ordersRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.productByOrderRepository = productByOrderRepository;
	}

	@Override
	public Orders createOrder(CreateOrderDTO request) {
		// Almacenar los datos primarios de la orden
		
		Orders element = new Orders();
	
		User user = userRepository.findUserById(request.getOrder().getUserId());
		Address address = createOrder(request.getAddress());
		List<ProductByOrder> products = saveProducts(request.getList());
		
		element.setAmount(request.getOrder().getTotal());
		element.setSubtotal(request.getOrder().getSubtotal());
		element.setReference(request.getOrder().getReference());
		element.setStatusReference(request.getOrder().getStatusReference());
		element.setMethodPayment(request.getOrder().getMethodPayment());
		element.setStatus(1);
		element.setAddress(address);
		element.setProducts(products);
		element.setUser(user);
		element.setCreatedAt(new Date());
		updateQuantity(request.getList());
		
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
			Product product = productRepository.findProductByCodeAndSizeAndId(e.getCode(), e.getSize(), e.getProductId());
			// Calculate cantd
			int cantCurrently = product.getCantd();
			System.out.println(cantCurrently);
			int newCantd = cantCurrently - e.getCantd();
			System.out.println(newCantd);
			product.setCantd(newCantd);
			productRepository.save(product);
		}
	}
	
	
	// Nos permite generar una nueva dirección de entrega
	private Address createOrder(AddressDTO request) {
		Address address = new Address();
		address.setCalle(request.getCalle());
		address.setColonia(request.getColonia());
		address.setCp(request.getCp());
		address.setEmailNotification(request.getEmail());
		address.setMoreInformation(request.getDetails());
		address.setNames(address.getNames());
		address.setNoExterior(request.getNoInterior());
		address.setNoInterior(request.getNoInterior());
		address.setPhone(request.getPhone());
		address.setSurnames(request.getSurnames());
		address.setTown(request.getTown());
		address.setState(request.getState());
		address.setUserId(request.getUserId());
		address.setTypeSend(request.getTypeSend());
		Address newAddress = addressRepository.save(address);
		return newAddress;
	}
	
	private List<ProductByOrder> saveProducts(List<ProductsCantdDTO> request ) {
		
		List<ProductByOrder> list = new ArrayList<>();
		
		for(ProductsCantdDTO e :  request) {
			ProductByOrder element = new ProductByOrder();
			element.setCantd(e.getCantd());
			element.setCode(e.getCode());
			element.setCreatedAt(new Date());
			element.setDiscount(e.getDiscount());
			element.setName(e.getName());
			element.setPrice(e.getPrice());
			element.setProductId(e.getProductId());
			element.setSize(e.getSize());
			list.add(element);
		}
		
		productByOrderRepository.saveAll(list);
		return list;
		
	}
	
	
	
	
}
