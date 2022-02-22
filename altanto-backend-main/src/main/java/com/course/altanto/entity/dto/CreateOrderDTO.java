
package com.course.altanto.entity.dto;

import java.util.List;

import lombok.Data;

/**
 * @author gabriel.juarez
 *
 */

@Data
public class CreateOrderDTO {

	private AddressDTO address;

	private OrdersDTO order;	
	
	private List<ProductsCantdDTO> list;
	
}
