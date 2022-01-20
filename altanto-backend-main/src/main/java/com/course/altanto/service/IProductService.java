package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;
import com.course.altanto.entity.dto.ProductDTO;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IProductService {

	// View products 
	List<Product> seach();
	
	List<Product> getAllProducts();
	
	Product newProduct(ProductDTO request);
	
	Product editProduct(String id, ProductDTO request) throws ExceptionGeneric;
	
	Product viewProductById(String id) throws ExceptionGeneric;
	
	Product deleteProductById(String id) throws ExceptionGeneric;
	
}
