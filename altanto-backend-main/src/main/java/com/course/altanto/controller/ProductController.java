package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Product;
import com.course.altanto.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private IProductService service;
	
	public ProductController(IProductService service) {
		this.service = service;
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Product>> test(@PathVariable(value = "id") String id) {
		List<Product> response = service.getAllProducts();
		 return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> list(){
		List<Product> response = service.getAllProducts();
    	return new ResponseEntity<>(response, HttpStatus.OK);
     }
	
	
	
}
