package com.bms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.bms.dao.UserDAO;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNameCanNotBeEmpty;
import com.bms.model.AuthResponse;
import com.bms.model.CustomerData;
import com.bms.model.LoginDetails;
import com.bms.service.CustomerDetailsService;
import com.bms.service.JwtUtil;




@SpringBootTest
class AuthControllerTest {

	@InjectMocks
	AuthController authController;

	AuthResponse authResponse;

	UserDetails userdetails;

	@Mock
	JwtUtil jwtutil;

	@Mock
	CustomerDetailsService custdetailservice;

	@Mock
	UserDAO userservice;

	@Test
	 void loginTest() {
		
		String password = "1234swati";
		String customerEncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		CustomerData user = new CustomerData("swati", customerEncodedPassword, null);
		LoginDetails user1 = new LoginDetails("swati", password, null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
		UserDetails value = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		when(custdetailservice.loadUserByUsername(user.getUsername())).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user1);
		assertEquals( 200, login.getStatusCodeValue());
	}

	@Test
	 void loginTestFailed() {

		try {
			String password = "1234swati";
			String customerEncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
			CustomerData user = new CustomerData("swati", customerEncodedPassword, null);
			LoginDetails user1 = new LoginDetails("swati", "a3VtYXJANzky", null);
			UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
			UserDetails value = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
			when(custdetailservice.loadUserByUsername(user.getUsername())).thenReturn(value);
			when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
			authController.login(user1);
		} catch (UnauthorizedException ex) {
			assertEquals(ex.getMessage(), "User Name and Password are incorrect");
		}
	}
	
	@Test
	 void userNameCanNotBeEmptyTest() {

		try {
			String password = "1234swati";
			String customerEncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
			CustomerData user = new CustomerData("swati", customerEncodedPassword, null);
			LoginDetails user1 = new LoginDetails("", "a3VtYXJANzky", null);
			UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
			UserDetails value = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
			when(custdetailservice.loadUserByUsername(user.getUsername())).thenReturn(value);
			when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
			authController.login(user1);
		} catch (UserNameCanNotBeEmpty ex) {
			assertEquals(ex.getMessage(), "User Name cannot be empty");
		}
	}
	
	@Test
	 void userAlreadyLoginTest() {
		
		String password = "1234swati";
		String customerEncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		CustomerData user = new CustomerData("swati", customerEncodedPassword, null);
		LoginDetails user1 = new LoginDetails("swati", password, null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
		UserDetails value = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		when(custdetailservice.loadUserByUsername(user.getUsername())).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user1);
		ResponseEntity<?> loginAgain = authController.login(user1);
		assertEquals(403, loginAgain.getStatusCodeValue());
	}

	@Test
	 void validateTestValidtoken() {

		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("swati");
		CustomerData user1 = new CustomerData("swati", "1234swati",null);
		Optional<CustomerData> data = Optional.of(user1);
		when(userservice.findById("swati")).thenReturn(data);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("true"));
		
	}
	
	@Test
	 void validateTestValidtoken2() {

		String password = "1234swati";
		String customerEncodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		CustomerData user = new CustomerData("swati", customerEncodedPassword, null);
		LoginDetails user1 = new LoginDetails("swati", password, null);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
		UserDetails value = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
		when(custdetailservice.loadUserByUsername(user.getUsername())).thenReturn(value);
		when(jwtutil.generateToken(loadUserByUsername)).thenReturn("token");
		ResponseEntity<?> login = authController.login(user1);
		
		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("swati");
		CustomerData user2 = new CustomerData("swati", "1234swati",null);
		Optional<CustomerData> data = Optional.of(user2);
		when(userservice.findById("swati")).thenReturn(data);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("true"));
		
	}

	@Test
	 void validateTestInValidtoken() {

		when(jwtutil.validateToken("token")).thenReturn(false);
		ResponseEntity<?> validity = authController.getValidity("bearer token");
		assertEquals( true, validity.getBody().toString().contains("false"));
	}

	@Test
	 void logoutTest() {

		when(jwtutil.validateToken("token")).thenReturn(true);
		when(jwtutil.extractUsername("token")).thenReturn("swati");
		CustomerData user1 = new CustomerData("swati", "1234swati",null);
		Optional<CustomerData> data = Optional.of(user1);
		when(userservice.findById("swati")).thenReturn(data);
		ResponseEntity<?> validity = authController.logout("bearer token");
		assertEquals( 200, validity.getStatusCodeValue());
		
	}
	
	@Test
	 void logoutInValidTokenTest() {

		when(jwtutil.validateToken("token")).thenReturn(false);
		ResponseEntity<?> validity = authController.logout("bearer token");
		assertEquals( 200, validity.getStatusCodeValue());
		
	}

}
