package com.bms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bms.dao.UserDAO;
import com.bms.exception.UnauthorizedException;
import com.bms.model.CustomerData;



@SpringBootTest
 class ServiceTest {

	UserDetails userdetails;
	
	@InjectMocks
	CustomerDetailsService custdetailservice;

	@Mock
	UserDAO userservice;

	@Test
	 void loadUserByUsernameTest() {
		
		CustomerData user1=new CustomerData("axel123","axel1234",null);
		Optional<CustomerData> data =Optional.of(user1) ;
		when(userservice.findById("axel123")).thenReturn(data);
		UserDetails loadUserByUsername2 = custdetailservice.loadUserByUsername(user1.getUsername());
		assertEquals(user1.getUsername(),loadUserByUsername2.getUsername());
	}
	
	@Test
	 void loadUserByUsernameFalseTest() {
		
		CustomerData user=new CustomerData("swati","1234swati",null);
		Optional<CustomerData> data =Optional.of(user) ;
		when(userservice.findById("swati")).thenReturn(data);
		UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
		assertNotEquals(user.getUsername()+"false",loadUserByUsername.getUsername());
	}
	
	@Test
	 void loadFalseUserNameTest() {
		
		try {
			CustomerData user=new CustomerData("swati","1234swati",null);
			Optional<CustomerData> data =Optional.of(user) ;
			when(userservice.findById("swati")).thenReturn(data);
			UserDetails loadUserByUsername = custdetailservice.loadUserByUsername(user.getUsername());
		} catch (UnauthorizedException ex) {
			// TODO: handle exception
			assertEquals("Username Not Found",ex.getMessage());
		}
		
	}
	
}
