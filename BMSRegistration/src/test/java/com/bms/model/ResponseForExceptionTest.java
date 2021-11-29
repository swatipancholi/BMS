package com.bms.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

public class ResponseForExceptionTest {

	@InjectMocks
	ResponseForException responseForException;
	
	@Before
	public void init() {
		responseForException = new ResponseForException("Error",LocalDateTime.now(),HttpStatus.BAD_GATEWAY);
	}
	
	@Test
	public void testMessage() {
		assertEquals("Error", responseForException.getMessge());
	}
	
	@Test
	public void testDate() {
		assertEquals(LocalDateTime.now(), responseForException.getTimestamp());
	}
	
	@Test
	public void testStatus() {
		assertEquals(HttpStatus.BAD_GATEWAY, responseForException.getStatus());
	}
}
