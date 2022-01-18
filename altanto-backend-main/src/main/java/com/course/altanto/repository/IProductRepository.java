package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {}
