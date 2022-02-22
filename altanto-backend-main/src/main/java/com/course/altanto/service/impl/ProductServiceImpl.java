package com.course.altanto.service.impl;

import static com.course.altanto.constant.FileConstant.DIRECTORY_CREATED;
import static com.course.altanto.constant.FileConstant.DOT;
import static com.course.altanto.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.course.altanto.constant.FileConstant.JPG_EXTENSION;
import static com.course.altanto.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static com.course.altanto.constant.FileConstant.PRODUCT_FOLDER;
import static com.course.altanto.constant.FileConstant.PRODUCT_IMAGE_PATH;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.altanto.entity.File;
import com.course.altanto.entity.Product;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.repository.IFileRepository;
import com.course.altanto.repository.IProductRepository;
import com.course.altanto.service.IProductService;

@Component
@Transactional
public class ProductServiceImpl implements IProductService {

	private IProductRepository productRepository;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private IFileRepository fileRepository;

	public ProductServiceImpl(IProductRepository productRepository, IFileRepository fileRepository) {
		this.productRepository = productRepository;
		this.fileRepository = fileRepository;
	}
	
	@Override
	public Page<Product> search(int pageNo, int pageSize,String codeProd, String  description, String  name, String  category) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Product> posts = productRepository.searchByFilters(codeProd, description, name, category, pageable);
		return posts;
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
	public Product newProduct(String codeProd, String name, String description, int cant, String size, double price,
			int discount, String category, String typeGarment,String typeClothing, List<MultipartFile> images) throws NotAnImageFileException, IOException, ExceptionGeneric {

		List<File> list = new ArrayList<>();
		
		validateProductByCodeAndSize(codeProd, size);
		Product element = new Product();
		element.setCode(codeProd);
		element.setName(name);
		element.setShortDescription(description);
		element.setCantd(cant);
		element.setPrice(price);
		element.setDiscount(discount);
		element.setSize(size);
		element.setCategory(category);
		element.setTypeGarment(typeGarment);
		element.setTypeClothing(typeClothing);
		element.setCreatedAt(new Date());
		
		// Validamos que todos los archivos adjuntados sean de tipo imagen o descendientes de este.
		for(MultipartFile file : images) {
			if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())) {
                throw new NotAnImageFileException(file.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
		}
		
		for(MultipartFile file :  images) {
		    File entityFile = new File();
			File entity = saveImage(codeProd,entityFile, file);
			list.add(entity);
		}
		
		element.setImages(list);
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
	public Product deleteProductById(String id) throws ExceptionGeneric {
		Product element = validateProductExist(id);
		productRepository.deleteById(element.getId());
		return element;
	}

	@Override
	public Product getPorductByCodeProd(String codeProd) throws ExceptionGeneric {
		Product response = getProductsByCode(codeProd);
		return response;
	}

	
	private Product validateProductExist(String id) throws ExceptionGeneric {
		Product element = productRepository.findProductById(id);
		if (element == null) {
			throw new ExceptionGeneric("No hemos encontrado el producto");
		}
		return element;
	}


	
	
	
	
	private File saveImage(String codeProd, File entity,MultipartFile profileImage) throws IOException, NotAnImageFileException {
         if (profileImage != null) {
            
        	final String uuid = UUID.randomUUID().toString().toLowerCase();
        	  
        	Path userFolder = Paths.get(PRODUCT_FOLDER + uuid).toAbsolutePath().normalize();
        	  
            if(!Files.exists(userFolder)) {
                  Files.createDirectories(userFolder);
                  LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
              
            Files.deleteIfExists(Paths.get(userFolder + uuid + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(uuid + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            entity.setRouteFile(setProfileImageUrl(uuid));
            entity.setNameFile(uuid);
            entity.setNameEntity("PRODUCT_" + codeProd);
            entity.setRegCreatedBy("");
            entity.setRegDateCreated(new Date());
            fileRepository.save(entity);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
		return entity;
    }
	
	@Override
	public Product getProductByCodeAndSize(String code, String size) throws ExceptionGeneric {
		Product element = getProductsByCodeAndSize(code, size);
		return element;
	}
	
	private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(PRODUCT_IMAGE_PATH + username + "/"
        + username + DOT + JPG_EXTENSION).toUriString();
    }

	@Override
	public List<Product> getProductsByOrder(List<String> ids) {
		List<Product> response = productRepository.findAllById(ids);
		return response;
	}

	@Override
	public Page<Product> searchEC(String typeClothing, String clasification, String category, String keyWord,int minPrice, int maxPrice, int pageNo, int pageSize) {
		  Pageable pageable = PageRequest.of(pageNo, pageSize);   
		  Page<Product> response = productRepository.search(typeClothing, clasification, category, keyWord, minPrice, maxPrice, pageable);
	      return response;
	}

	@Override
	public List<Product> findProductTopRating() {
		return productRepository.findTopRating();
	}

	@Override
	public List<Product> getProductsByCodeL(String code) {
		return productRepository.findProductByCode(code);
	}
	
	private Product getProductsByCode(String codeProd) throws ExceptionGeneric {

		Product element = productRepository.findProductByCodeNative(codeProd);
		
		if (element == null) {
			throw new ExceptionGeneric("No se encontro un producto con el código: " + codeProd);
		}
		
		return element;
	}
	
	private Product validateProductByCodeAndSize(String code, String size) throws ExceptionGeneric {

		Product element = productRepository.findProductByCodeAndSize(code, size);
		
		if (element != null) {
			throw new ExceptionGeneric("Ya existe un producto con esta talla");
		}
		
		return element;
	}
	
	private Product getProductsByCodeAndSize(String code, String size) throws ExceptionGeneric {

		Product element = productRepository.findProductByCodeAndSize(code, size);
		
		if (element == null) {
			throw new ExceptionGeneric("No se encontro el producto con esta talla");
		}
		
		return element;
	}

	
	

}
