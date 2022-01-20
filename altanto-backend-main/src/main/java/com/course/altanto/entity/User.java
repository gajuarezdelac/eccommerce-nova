package com.course.altanto.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long id;
	
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
	
	@Column(nullable = false)
	private String role;
	
	private String token;
	
	private Date expireToken;
	
	private String profileImageUrl;
	
	private Date joinDate;
	
	private String[] authorities;
	
	private boolean isActive;
	
	private boolean isNotLocked;
	
    private Date lastLoginDate;
    
    private Date lastLoginDateDisplay;
   
}
