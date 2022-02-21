package com.course.altanto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.altanto.entity.CityINEGI;

public interface ICityRepository extends JpaRepository<CityINEGI, Long> {
	
	List<CityINEGI> findCityINEGIByClave(String clave);

}
