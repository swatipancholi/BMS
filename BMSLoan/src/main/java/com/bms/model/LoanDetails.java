package com.bms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.bms.validators.LoanType;

import lombok.Data;

@Entity
@Data
@Table(name = "Loan_details")
public class LoanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
	private Long loan_id;

	@Pattern(regexp = "[a-zA-Z0-9]*$", message = "Customer name should contain only alpha-numerics")
	@Column
	private String username;

	@Min(value = 0, message = "A positive Loan Amount mandatory")
	@Column(name = "loan_amount")
	private int loanAmount;

	@Min(value = 0, message = "A positive Loan Duration is mandatory")
	@Column(name = "duration_years")
	private int duration;

	@Min(value = 0, message = "A positive Rate of Interest is mandatory")
	@Column(name = "rate_of_interest")
	private float rateOfInterest;

	@Column
	private Date date;

	@LoanType
	@NotBlank(message = "Loan Type  is mandatory ")
	@Column
	private String loanType;

}
