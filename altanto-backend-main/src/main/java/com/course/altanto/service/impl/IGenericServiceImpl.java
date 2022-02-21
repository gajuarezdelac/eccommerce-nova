package com.course.altanto.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.course.altanto.entity.CityINEGI;
import com.course.altanto.entity.StateINEGI;
import com.course.altanto.repository.ICityRepository;
import com.course.altanto.repository.IStateRepository;
import com.course.altanto.service.IGenericService;

@Component
@Transactional
public class IGenericServiceImpl implements IGenericService{

	@Autowired
	private IStateRepository stateRepository;
	
	@Autowired
	private ICityRepository cityRepository;
	
	
	@Override
	public List<StateINEGI> getAllStates() {
		return stateRepository.findAll();
	}

	@Override
	public List<CityINEGI> getCitiesByClave(String clave) {
		return cityRepository.findCityINEGIByClave(clave);
	}
	
	
}
