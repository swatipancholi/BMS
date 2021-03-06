package com.bms.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bms.dao.UserDAO;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNameCanNotBeEmpty;
import com.bms.model.AuthResponse;
import com.bms.model.CustomerData;
import com.bms.model.LoginDetails;
import com.bms.service.CustomerDetailsService;
import com.bms.service.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Api(produces = "application/json", value = "Creating and validating the Jwt token")
public class AuthController {

	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private CustomerDetailsService custdetailservice;
	@Autowired
	private UserDAO userservice;

	private List<String> loggedInUsers = new ArrayList<String>();

	/**
	 * 
	 * @param userlogincredentials
	 * @return
	 */
	@ApiOperation(value = "Verify credentials and generate JWT Token", response = ResponseEntity.class)
	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody LoginDetails userlogincredentials)  throws UserNameCanNotBeEmpty, UnauthorizedException{
		// Generates token for login
		if(userlogincredentials.getUsername().isEmpty()) {
			throw new UserNameCanNotBeEmpty("User Name cannot be empty");
		}
		final UserDetails userdetails = custdetailservice.loadUserByUsername(userlogincredentials.getUsername());
//		System.out.println(userlogincredentials.getUsername() +" " + userlogincredentials.getPassword());
//		System.out.println(userdetails.getUsername() +" " + userdetails.getPassword());
		byte[] actualByte = Base64.getDecoder().decode(userdetails.getPassword());
		String userDecodedPassword = new String(actualByte);
		String username = "";
		String generateToken = "";

		if (userDecodedPassword.equals(userlogincredentials.getPassword())) {
			username = userlogincredentials.getUsername();
			if (loggedInUsers.contains(username)) {
				log.info("User: " + username + "");
				return new ResponseEntity<>("User Already Logged-in", HttpStatus.FORBIDDEN);
			}
			generateToken = jwtutil.generateToken(userdetails);
			loggedInUsers.add(username);
			log.info("login successful");
			return new ResponseEntity<>(new CustomerData(username, null, generateToken), HttpStatus.OK);
		} else {
			log.info("At Login : ");
			log.error("Not Accesible");
//			return new ResponseEntity<>("user name and password are incorrect", HttpStatus.FORBIDDEN);
			throw new UnauthorizedException("User Name and Password are incorrect");
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Validate JWT Token", response = ResponseEntity.class)
	@GetMapping(value = "/validate")
	public ResponseEntity<Object> getValidity(@RequestHeader("Authorization") final String token) {
		// Returns response after Validating received token
		String token1 = token.substring(7);
		AuthResponse res = new AuthResponse();
		if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
			String username = jwtutil.extractUsername(token1);
			res.setUsername(username);
			res.setValid(true);
			Optional<CustomerData> user1 = userservice.findById(username);
			if (user1.isPresent() && loggedInUsers.contains(username)) {
				res.setUsername(user1.get().getUsername());
				res.setMessage("token successfully validated");
				res.setLoggedIn(true);
				log.info("token successfully validated");
			} else {
				res.setLoggedIn(false);
				res.setMessage("Please Login First");
				log.info("Cannot process request as User is logged out");
			}
		} else {
			res.setValid(false);
			res.setMessage("Invalid Token Received");
			log.info("At Validity : ");
			log.error("Token Has Expired");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}


	@GetMapping(value = "/log-out")
	public ResponseEntity<Object> logout(@RequestHeader("Authorization") final String token) {
		// Returns response after Validating received token
		String token1 = token.substring(7);
		AuthResponse res = new AuthResponse();
		if (Boolean.TRUE.equals(jwtutil.validateToken(token1))) {
			String username = jwtutil.extractUsername(token1);
			Optional<CustomerData> user1 = userservice.findById(username);
			if (user1.isPresent()) {
				loggedInUsers.remove(username);
				res.setUsername(username);
				res.setMessage("Logout Successfull");
			}
		} else {
			res.setValid(false);
			res.setMessage("Invalid Token Received");
			log.info("At Validity : ");
			log.error("Token Has Expired");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}