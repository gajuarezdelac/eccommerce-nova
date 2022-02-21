package com.course.altanto.service;

import java.util.List;

import com.course.altanto.entity.CityINEGI;
import com.course.altanto.entity.StateINEGI;

public interface IGenericService {

   List<StateINEGI> getAllStates();
	
   List<CityINEGI> getCitiesByClave(String clave);
	
}
