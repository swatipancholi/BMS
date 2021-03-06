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
		validateToken = new ValidateToken("R-101","swati",true,true);
	}
	
	@Test
	public void testUserName() {
		assertEquals("swati", validateToken.getName());
	}
	
	@Test
	public void testUID() {
		assertEquals("R-101", validateToken.getUid());
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
