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

import com.bms.dao.UserDAO;
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
		
		CustomerData user1=new CustomerData("swati","1234swati",null);
		Optional<CustomerData> data =Optional.of(user1) ;
		when(userservice.findById("swati")).thenReturn(data);
		UserDetails loadUserByUsername2 = custdetailservice.loadUserByUsername("swati");
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

	
}
