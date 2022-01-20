package com.course.altanto.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.Product;
import com.course.altanto.entity.dto.ProductDTO;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IProductService {

	// View products
	Page<Product> search(int pageNo, int pageSize, String sortBy, String sortDir);

	List<Product> getAllProducts();

	Product newProduct(String codeProd, String name, String description, int cant, String size, double price,
			int discount, String category, String typeGarment, List<MultipartFile> images);

	Product editProduct(String id, String name, String description, int cant, String size, double price,int discount) throws ExceptionGeneric;

	Product viewProductById(String id) throws ExceptionGeneric;

	Product deleteProductById(String id) throws ExceptionGeneric;

	List<Product> getPorductByCodeProd(String codeProd) throws ExceptionGeneric;

}
