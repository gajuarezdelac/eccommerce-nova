package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;

	@Column(nullable = false)
	private String consecutive;
	
	@Column(nullable = false)
	private String routeFile;
	
	@Column(nullable = false)
	private String nameEntity;
	
	@Column(nullable = false)
	private String nameFile;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private Date regDateCreated;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String regCreatedBy;

}
