package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
	
	private double discount;
		
	private double subtotal;
	
	private double amount;
	
	private String reference;
	
	private String statusReference;
	
	private String methodPayment;
    
	private int status;
	
	@ManyToMany(targetEntity = ProductByOrder.class,cascade = CascadeType.ALL)
	private List<ProductByOrder> products;
	
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private User user;
	
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 	private Address address;
    
	private Date createdAt;
	
	private Date EditAt;
	
	
}
