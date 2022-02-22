/**
 * 
 */
package com.course.altanto.entity.dto;

import javax.persistence.Column;

import lombok.Data;

/**
 * @author gabriel.juarez
 *
 */


@Data
public class ProductsCantdDTO {
	
	private String code;
	
	private String productId;

	private String name;

	private int cantd;
	
	private String size;
	
	private double price;
	
	private int discount;
	
}
