package com.course.altanto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.Address;
import com.course.altanto.entity.dto.AddressDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IAddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	
	private IAddressService service;
	
	private AddressController(IAddressService service) {
		this.service = service;
	}
	
	
	@GetMapping("/view/{id}")
	public ResponseEntity<Address> test(@PathVariable(value = "id") String id) throws ExceptionGeneric {
		Address response = service.findAddressByID(id);
			 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@GetMapping("/list/{userId}")
	public ResponseEntity<List<Address>> list(@PathVariable(value = "userId") String userId){
	    	List<Address> list = service.getAllAddressByUser(userId);
	    	return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Address> newAddress(@RequestBody AddressDTO request){
		Address list = service.newAddress(request);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	@PostMapping("/update")
	public ResponseEntity<Address> updateAddress(@RequestBody AddressDTO request) throws ExceptionGeneric{
		Address list = service.updateAddress(request);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Address> deleteAddrees(@PathVariable String id) throws ExceptionGeneric{
		Address list = service.removeAddress(id);
    	return new ResponseEntity<>(list, HttpStatus.OK);
    }

	
	
	
	
}
