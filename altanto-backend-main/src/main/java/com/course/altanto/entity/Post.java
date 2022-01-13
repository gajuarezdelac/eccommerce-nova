/**
 * 
 */
package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Post implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private Date dateCreation;
	
	@Column(nullable = false)
	private String urlImage;
	
}
