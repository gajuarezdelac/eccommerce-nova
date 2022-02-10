package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(nullable = false, updatable = false)
	private String id;
	
	@Column(nullable = false)
	private String names;
	
	@Column(nullable = false)
	private String surnames;
	
	@Column(nullable = false)	
	private String username;
	
	@Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String gender;
	
	private Date dateOfBirth;
	
	private String numberPhone;
	
	@Column(nullable = false)
	private String role;
	
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String token;
	
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Date expireToken;
	
	private String profileImageUrl;
	
	private Date joinDate;
	
	private String[] authorities;
	
	private String location;
	
	private boolean isActive;
	
	private boolean isNotLocked;
	
    private Date lastLoginDate;
    
    private Date lastLoginDateDisplay;
   
}
