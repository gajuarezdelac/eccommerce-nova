package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IProductService {

	List<Product> seach();
	
	List<Product> getAllProducts();
	
	Product newProduct(Product request);
	
	Product editProduct(Product request);
	
	Product viewProductById(String id) throws ExceptionGeneric;
	
	Product deleteProductById(String id) throws ExceptionGeneric;
	
}
