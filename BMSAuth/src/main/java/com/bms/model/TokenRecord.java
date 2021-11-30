package com.bms.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Token_Record")
//@Data
@Getter
@Setter
@NoArgsConstructor
public class TokenRecord {

	@Id
	String username;

	@NotBlank
	String token;

	Date issuedAt;
	Date expireAt;
}
