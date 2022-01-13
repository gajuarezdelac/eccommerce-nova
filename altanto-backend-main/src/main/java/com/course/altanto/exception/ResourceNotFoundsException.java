/**
 * 
 */
package com.course.altanto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author gabriel.juarez
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public String resourceName;
	
	public String fielName;
	
	public String fieldValue;
	
	

	/**
	 * @param resourceName
	 * @param fielName
	 * @param fieldValue
	 */
	public ResourceNotFoundsException(String resourceName, String fielName, String fieldValue) {
		super(String.format("%s not found with %s: '%s'", resourceName,fielName, fieldValue));
		this.resourceName = resourceName;
		this.fielName = fielName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFielName() {
		return fielName;
	}

	public void setFielName(String fielName) {
		this.fielName = fielName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

}
