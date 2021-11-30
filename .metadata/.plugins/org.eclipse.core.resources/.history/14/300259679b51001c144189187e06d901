package com.bms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bms.validators.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer_details")
public class Customer {
	
	@JsonIgnore
	@Id
	private String customer_id;
	
	@NotBlank(message = "Customer name is mandatory ")
	@Pattern(regexp = "[a-zA-Z ]*$", message = "Customer name should contain only alphabets and space")
	@Column
	private String name;
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginDetails_id", referencedColumnName = "username")
	private CustomerData loginDetails;
	
	@NotBlank(message = "Address is mandatory ")
	@Column
	private String address;
	
	@NotBlank(message = "State Name is mandatory ")
	@Column
	private String state;
	
	@NotBlank(message = "Country Name is mandatory ")
	@Column
	private String country;
	
	@NotBlank(message = "Email is mandatory ")
	@Email
	@Column
	private String emailAddress;
	
	@Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "Contact Number should contain only 10 digitis  and should start with 6,7,8,9")
	@Column
	private String contactNo;
	
	@NotNull(message = "Date Of Birth  is mandatory ")
	@Past
	@Column
	private Date dob;
	
	@NotBlank(message = "Identification Document Number  is mandatory ")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-z]{1}", message = "Enter valid Pan Card Number ")
	@Column
	private String identificationNumber;
	
	@AccountType
	@NotBlank(message = "Account Type  is mandatory ")
	@Column
	private String accountType;
	

}
