package com.course.altanto.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Orders;

@Repository
public interface IInboxRepository extends JpaRepository<Orders, String> {}
