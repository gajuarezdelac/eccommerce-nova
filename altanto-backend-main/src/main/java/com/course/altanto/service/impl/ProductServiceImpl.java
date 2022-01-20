package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;
import com.course.altanto.entity.dto.ProductDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IProductRepository;
import com.course.altanto.service.IProductService;

@Component
@Transactional
public class ProductServiceImpl implements IProductService {

	
	private IProductRepository productRepository;
	
	public ProductServiceImpl(IProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public Product newProduct(ProductDTO request) {
		
		Product element = new Product();
		
	
		
		return null;
	}

	@Override
	public Product editProduct(String id, ProductDTO request) throws ExceptionGeneric {
		
		Product element = validateProductExist(id);
		
		
		return null;
	}

	@Override
	public List<Product> seach() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	@Override
	public Product viewProductById(String id) throws ExceptionGeneric {
		Product element = validateProductExist(id);
		return element;
	}

	@Override
	public Product deleteProductById(String id) throws ExceptionGeneric {
		Product element = validateProductExist(id);
		productRepository.deleteById(element.getId());
		return element;
	}

	
	private Product validateProductExist(String id) throws ExceptionGeneric {
		
		Product element = productRepository.findProductById(id);
		if(element == null ) {
			 throw new ExceptionGeneric("No existe el producto seleccionado");
		}	
		return element;
	}


	
	
	

}
