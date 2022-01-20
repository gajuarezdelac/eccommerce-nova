package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<List<Product>> list() {
		List<Product> response = service.getAllProducts();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestParam("codeProd") String codeProd,
			                                  @RequestParam("name") String name,
			                                  @RequestParam("description") String description,
			                                  @RequestParam("cant") int cant,
			                                  @RequestParam("size") String size,
			                                  @RequestParam("price") double price,
			                                  @RequestParam("discount") int discount,
			                                  @RequestParam("category") String category,
			                                  @RequestParam("typeGarment") String typeGarment,
			                                  @RequestParam("images") List<MultipartFile> images) {
		
		Product response = service.newProduct(codeProd,name, description, cant, size, price, discount, category, typeGarment, images);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

}
