package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.course.altanto.entity.Address;
import com.course.altanto.entity.dto.AddressDTO;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.repository.IAddressRepository;
import com.course.altanto.service.IAddressService;

@Component
@Transactional
public class AddressServiceImpl implements IAddressService{
	
	private IAddressRepository addressRepository;
	
	public AddressServiceImpl(IAddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public List<Address> getAllAddressByUser(String userId) {
		return addressRepository.findAddressByUserId(userId);
	}

	@Override
	public Address newAddress(AddressDTO request) {
		
		Address entity = new Address();
		entity.setUserId(request.getUserId());
		entity.setCalle(request.getCalle());
		entity.setColonia(request.getColonia());
		entity.setCp(request.getCp());
		entity.setEmailNotification(request.getEmail());
		entity.setMoreInformation(request.getDetails());
		entity.setNames(request.getNames());
		entity.setNoExterior(request.getNoExterior());
		entity.setNoInterior(request.getNoInterior());
		entity.setPhone(request.getPhone());
		entity.setSurnames(request.getSurnames());
		entity.setTown(request.getTown());
		entity.setYourHome(request.isYourHome());
		addressRepository.save(entity);
		return entity;
	}

	@Override
	public Address removeAddress(String id) throws ExceptionGeneric {
		Address response =  validateInboxExist(id);
		addressRepository.deleteById(id);
		return response;
	}

	@Override
	public Address updateAddress(AddressDTO request) throws ExceptionGeneric {
		
		Address entity = validateInboxExist(request.getId());
		entity.setCalle(request.getCalle());
		entity.setColonia(request.getColonia());
		entity.setCp(request.getCp());
		entity.setEmailNotification(request.getEmail());
		entity.setMoreInformation(request.getDetails());
		entity.setNames(request.getNames());
		entity.setNoExterior(request.getNoExterior());
		entity.setNoInterior(request.getNoInterior());
		entity.setPhone(request.getPhone());
		entity.setSurnames(request.getSurnames());
		entity.setTown(request.getTown());
		entity.setYourHome(request.isYourHome());
		addressRepository.save(entity);
		
		return entity;
	}
	
	@Override
	public Address findAddressByID(String id) throws ExceptionGeneric {
		Address entity = validateInboxExist(id);
		return entity;
	}
	
	private Address validateInboxExist(String id) throws ExceptionGeneric {
		
		Address element = addressRepository.findAddressById(id);
		
		if(element == null ) {
			 throw new ExceptionGeneric("No existe la dirección");
		}
		
		return element;
	}

	

	

}
