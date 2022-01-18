package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;
import com.course.altanto.service.IProductService;

@Component
@Transactional
public class ProductServiceImpl implements IProductService {

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product newProduct(Product request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product editProduct(Product request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product viewProductByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProductByCode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> seach() {
		// TODO Auto-generated method stub
		return null;
	}

}
