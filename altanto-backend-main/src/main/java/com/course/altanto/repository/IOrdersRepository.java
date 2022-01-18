package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.altanto.entity.Orders;

public interface IOrdersRepository extends JpaRepository<Orders, String> {}

