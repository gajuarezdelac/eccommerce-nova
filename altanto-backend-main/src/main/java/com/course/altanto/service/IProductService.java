package com.course.altanto.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.Product;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;

@Component
public interface IProductService {

	// View products 
	Page<Product> searchEC(String typeClothing, String clasification, String category,String keyWord, int pageNo, int pageSize); 
	
	// View products ADMIN
	Page<Product> search(int pageNo, int pageSize,String codeProd, String  description, String  name, String  category);

	List<Product> getAllProducts();

	Product newProduct(String codeProd, String name, String description, int cant, String size, double price,
			int discount, String category, String typeGarment, String typeClothing, List<MultipartFile> images) throws NotAnImageFileException, IOException;

	Product editProduct(String id, String name, String description, int cant, String size, double price,int discount) throws ExceptionGeneric;

	Product viewProductById(String id) throws ExceptionGeneric;

	Product deleteProductById(String id) throws ExceptionGeneric;

	List<Product> getPorductByCodeProd(String codeProd) throws ExceptionGeneric;
	
	List<Product> getProductsByOrder(List<String> ids);
	
	List<Product> findProductTopRating();
	

}
