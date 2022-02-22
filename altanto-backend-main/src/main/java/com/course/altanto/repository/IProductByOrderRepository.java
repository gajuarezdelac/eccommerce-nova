package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.course.altanto.entity.ProductByOrder;

public interface IProductByOrderRepository  extends JpaRepository<ProductByOrder, String> {

}
