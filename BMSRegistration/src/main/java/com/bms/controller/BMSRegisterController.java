package com.bms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bms.exception.InitialDepositException;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNotLoggedInException;
import com.bms.model.Customer;
import com.bms.model.ResponseForSuccess;
import com.bms.service.RegisterService;

@RestController
public class BMSRegisterController {

	@Autowired
	private RegisterService registerService;

	@PostMapping(value = "/customer/register")
	public ResponseEntity<ResponseForSuccess> register(@Valid @RequestBody Customer customer)
			throws UnauthorizedException, InitialDepositException, UserNotLoggedInException {

		return registerService.register(customer);
	}

	@PutMapping(value = "/customer/{customer_id}/updateDetails")
	public ResponseEntity<ResponseForSuccess> editDetails(@RequestHeader("Authorization") String token,
			@Valid @RequestBody Customer customer, @PathVariable String customer_id)
			throws InvalidTokenException, UnauthorizedException, UserNotLoggedInException {
		return registerService.editDetails(token, customer, customer_id);
	}

	@GetMapping(value = "/customer/{customer_id}/getDetails")
	public ResponseEntity<Object> getUser(@RequestHeader("Authorization") String token,
			@PathVariable String customer_id) 
					throws InvalidTokenException, UnauthorizedException, UserNotLoggedInException{
		return registerService.getCustomerDetails(token, customer_id);
	}

}
