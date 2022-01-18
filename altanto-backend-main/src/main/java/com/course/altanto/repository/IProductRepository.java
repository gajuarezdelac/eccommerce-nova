package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.course.altanto.entity.Product;

public interface IProductRepository extends JpaRepository<Product, String> {}
