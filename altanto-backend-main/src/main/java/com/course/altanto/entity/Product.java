package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author gabriel.juarez
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String shortDescription;

	private int cantd;
	
	private String size;
	
	@Column(nullable = false)
	private double price;
	
	private int discount;
	
	private String category;
	
	private double rating;
	
	private String typeGarment;
	
	@ManyToMany(targetEntity = File.class,cascade = CascadeType.ALL)
	private List<File> images;
	
	private Date createdAt;
	
	private Date editAt;
	
	
}
