package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {
	
	List<Product> findProductByCode(String code);
	
	Product findProductById(String id);
	
	@Query(value = "SELECT * FROM product p WHERE p.code LIKE %:code_prod% AND p.short_description LIKE %:desc% AND p.name LIKE %:name%  AND p.category LIKE %:category% ",nativeQuery = true)
	Page<Product> searchByFilters(@Param("code_prod") String codeProd,@Param("desc") String description,@Param("name") String name,@Param("category") String category, Pageable pageable);
	
	
	@Query(value = "SELECT * FROM product p",nativeQuery = true)
	Page<Product> search(Pageable pageable);
	
	
	@Query(value = "SELECT * FROM product p ORDER BY p.rating DESC LIMIT 6",nativeQuery = true)
	List<Product> findTopRating();
	
	List<Product> findAllByIdIn(List<String> ids);
	
}
