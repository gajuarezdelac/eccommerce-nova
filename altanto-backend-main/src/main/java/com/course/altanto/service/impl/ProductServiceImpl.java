package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.Product;
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
	public Page<Product> search(int pageNo, int pageSize, String sortBy, String sortDir) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Product> posts = productRepository.findAll(pageable);		  
		return posts;
	}
	
	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product newProduct(String codeProd, String name, String description, int cant, String size, double price,
			int discount, String category, String typeGarment, List<MultipartFile> images) {

		Product element = new Product();
		element.setCode(codeProd);
		element.setName(name);
		element.setShortDescription(description);
		element.setCantd(cant);
		element.setPrice(price);
		element.setDiscount(discount);
		element.setCategory(category);
		element.setTypeGarment(typeGarment);
		
		productRepository.save(element);
	
		return element;
	}

	@Override
	public Product editProduct(String id, String name, String description, int cant, String size, double price,int discount) throws ExceptionGeneric {

		Product element = validateProductExist(id);
		element.setName(name);
		element.setShortDescription(description);
		element.setCantd(cant);
		element.setSize(size);
		element.setPrice(price);
		element.setDiscount(discount);
		
		return element;
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

	@Override
	public List<Product> getPorductByCodeProd(String codeProd) throws ExceptionGeneric {
		List<Product> response = getProductsByCode(codeProd);
		return response;
	}

	private Product validateProductExist(String id) throws ExceptionGeneric {
		Product element = productRepository.findProductById(id);
		if (element == null) {
			throw new ExceptionGeneric("No existe el producto seleccionado");
		}
		return element;
	}

	private List<Product> getProductsByCode(String codeProd) throws ExceptionGeneric {

		List<Product> list = productRepository.findProductByCode(codeProd);

		if (list.size() == 0) {
			throw new ExceptionGeneric("No se encontro un producto con el código: " + codeProd);
		}

		return list;
	}
	

}
