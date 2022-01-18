package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class Orders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Column(nullable = false)
	private String codeProd;
		
	@Column(nullable = false)
	private String userId;
	
	private double amount;
	
	private String[] listProducts;
	
	private int status;
	
	private Date createAt;
	
	private Date EditAt;
}
