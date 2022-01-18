package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Orders;
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

    @GetMapping("/list")
    public ResponseEntity<List<Orders>> list(){
    	List<Orders> list = service.getAllOrders();
    	return new ResponseEntity<>(list, HttpStatus.OK);
     }
	
}
