package com.bms.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Login_details")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerData {
	@Id
	@NotBlank(message = "Username is mandatory ")
	@Pattern(regexp = "[a-zA-Z0-9]*$", message = "user name should contain only alphabets and digits")
	private String username;
	
	@NotBlank(message = "Password is mandatory")
	@Size(min = 8, message = "minimum 8 Characters required")
	@Column
	private String password;
	
	@JsonIgnore
	@OneToOne(mappedBy = "loginDetails")
	private Customer customer_id;
	@Transient
	private String authToken;
}
