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
	
	Product findProductByCodeAndSize(String code, String size);
	
	List<Product> findProductByCode(String code);
	
	Product findProductById(String id);
	
	@Query(value = "SELECT * FROM product p WHERE p.code LIKE %:code_prod% AND p.short_description LIKE %:desc% AND p.name LIKE %:name%  AND p.category LIKE %:category% ",nativeQuery = true)
	Page<Product> searchByFilters(@Param("code_prod") String codeProd,@Param("desc") String description,@Param("name") String name,@Param("category") String category, Pageable pageable);
	
	@Query(value = "SELECT * FROM product p WHERE p.name LIKE %:keyword% AND p.category LIKE %:category% AND p.type_garment LIKE %:clasf% AND p.type_clothing LIKE %:type% GROUP BY p.code ORDER BY p.name ASC, p.discount DESC ",nativeQuery = true)
	Page<Product> search(@Param("type") String typeCloth,@Param("clasf") String clasif,@Param("category") String category,@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value = "SELECT * FROM product p ORDER BY p.rating DESC LIMIT 6",nativeQuery = true)
	List<Product> findTopRating();
	
	@Query(value= "SELECT * FROM product as p WHERE p.code = :code LIMIT 1", nativeQuery = true)
	Product findProductByCodeNative(@Param("code") String code);
	
	List<Product> findAllByIdIn(List<String> ids);
	
}
