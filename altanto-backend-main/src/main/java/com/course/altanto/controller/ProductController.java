package com.course.altanto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private IProductService service;
	
	public ProductController(IProductService service) {
		this.service = service;
	}
	
	
	
}
