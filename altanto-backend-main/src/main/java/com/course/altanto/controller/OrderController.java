package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Orders;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IOrdersService;

@RestController
@RequestMapping("/order")
public class OrderController {

	
	private IOrdersService service;
	
	public OrderController(IOrdersService service) {
		this.service = service;
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Orders>> test(@PathVariable(value = "id") String id) {
		List<Orders> response = service.viewOrderByUser(id);
		 return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Orders> getOrderById(@PathVariable(value = "id") String id) {
		Orders response = service.viewOrderById(id);
		return new ResponseEntity<Orders>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Orders> deleteOrderById(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Orders response = service.deleteOrder(id);
		return new ResponseEntity<Orders>(response, HttpStatus.OK);
	}

    @GetMapping("/list")
    public ResponseEntity<List<Orders>> list(){
    	List<Orders> list = service.getAllOrders();
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders request) {
    	Orders response = service.createOrder(request);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/change-status/{status}/{id}")
    public ResponseEntity<Orders> changeStatus(@PathVariable("status") int status, @PathVariable("id") String id) {
    	Orders response = service.updateOrder(status, id);
    	return new ResponseEntity<>(response, HttpStatus.OK);    	
    }
    
    
    
        
	
}
