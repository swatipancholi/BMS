package com.bms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.model.LoanDetails;
import com.bms.service.LoanService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BMSLoanController {

	@Autowired
	private LoanService loanService;

	@ApiOperation(value = "Apply Loan", response = ResponseEntity.class)
	@PostMapping(value = "/loan/apply")
	public ResponseEntity<Object> register(@Valid @RequestBody LoanDetails loanDetails,
			@RequestHeader("Authorization") final String token) throws UnauthorizedException, InvalidTokenException {

		return loanService.apply(token, loanDetails);
	}

	@ApiOperation(value = "Get Loan Details", response = ResponseEntity.class)
	@GetMapping(value = "/loan/{username}")
	public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String token, @PathVariable String username)
			throws InvalidTokenException, UnauthorizedException {

		System.out.println(username);
		return loanService.getLoanDetails(token, username);
	}

}
