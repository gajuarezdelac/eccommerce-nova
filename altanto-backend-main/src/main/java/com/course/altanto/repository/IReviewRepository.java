package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.Product;

@Component
public interface IReviewRepository extends JpaRepository<Product, String> {}
