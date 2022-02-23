package com.course.altanto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.altanto.entity.CityINEGI;
import com.course.altanto.entity.StateINEGI;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.service.IGenericService;

@RestController
@RequestMapping("/generic")
public class GenericController {

	@Autowired
	private IGenericService service;
	
	@GetMapping("/cities/{key}")
	public ResponseEntity<List<CityINEGI>> getCitiesByClave(@PathVariable(value = "key") String id) throws ExceptionGeneric {
		List<CityINEGI> response = service.getCitiesByClave(id);
			 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/list-states")
	public ResponseEntity<List<StateINEGI>> getAllStates(){
		List<StateINEGI> list = service.getAllStates();
	    	return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
}
