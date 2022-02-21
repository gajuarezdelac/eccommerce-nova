/**
 * 
 */
package com.course.altanto.entity.dto;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import lombok.Data;

/**
 * @author gabriel.juarez
 *
 */

@Data
public class CreateOrderDTO {

	private AddressDTO address;

	private OrderDto order;	
	
	private List<ProductsCantdDTO> list;
	
}
