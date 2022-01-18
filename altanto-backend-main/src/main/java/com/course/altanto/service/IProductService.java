package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;

@Component
public interface IProductService {

	List<Product> seach();
	
	List<Product> getAllProducts();
	
	Product newProduct(Product request);
	
	Product editProduct(Product request);
	
	Product viewProductByCode(String code);
	
	void deleteProductByCode();
	
}
