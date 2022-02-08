package com.course.altanto.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Address;
import com.course.altanto.entity.dto.AddressDTO;
import com.course.altanto.exception.ExceptionGeneric;

@Component
public interface IAddressService {
	
	Address findAddressByID(String id) throws ExceptionGeneric;
	
	List<Address> getAllAddressByUser(String userId);
	
	Address newAddress(AddressDTO request);

	Address removeAddress(String id) throws ExceptionGeneric;
	
	Address updateAddress(AddressDTO request) throws ExceptionGeneric;

}
