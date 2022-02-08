package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.altanto.entity.Address;

@Repository
public interface IAddressRepository  extends JpaRepository<Address, String> {
	
	Address findAddressById(String id);
		
	List<Address> findAddressByUserId(String userId);
	
}

