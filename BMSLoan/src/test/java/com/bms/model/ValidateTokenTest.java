package com.bms.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ValidateTokenTest {

	@InjectMocks
	ValidateToken validateToken;
	
	@Before
	public void init() {
		validateToken = new ValidateToken("Rishabh","Logged In",true,true);
	}
	
	@Test
	public void testUserName() {
		assertEquals("Rishabh", validateToken.getUsername());
	}
	
	@Test
	public void testMessage() {
		assertEquals("Logged In", validateToken.getMessage());
	}
	
	@Test
	public void testIsValid() {
		assertEquals(true,validateToken.isValid());
	}
	
	@Test
	public void testIsLoggedIn() {
		assertEquals(true, validateToken.isLoggedIn());
	}
}
