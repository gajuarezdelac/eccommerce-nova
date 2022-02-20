package com.course.altanto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.altanto.entity.StateINEGI;

public interface IStateRepository extends JpaRepository<StateINEGI, String> {
	
	StateINEGI findStateINEGIByClave(String clave);
	
}
