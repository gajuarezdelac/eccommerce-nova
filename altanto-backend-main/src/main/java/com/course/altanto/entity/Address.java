package com.course.altanto.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	
	private String userId;
	
	private String names;
	
	private String surnames;
	
	private boolean isYourHome;
	
	private String town;
	
	private String cp;
	
	private String phone;
	
	private String emailNotification;
	
	private String calle;
	
	private String colonia;
	
	private String noExterior;
	
	private String noInterior;
	
	private String moreInformation;
	
}
