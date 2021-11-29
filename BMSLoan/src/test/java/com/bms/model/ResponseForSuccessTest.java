package com.bms.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class ResponseForSuccessTest {

	@InjectMocks
	ResponseForSuccess responseForSuccess;
	
	@Before
	public void init() {
		responseForSuccess = new ResponseForSuccess("OK","","");
	}
	
	@Test
	public void testMessage() {
		assertEquals("OK", responseForSuccess.getMessage());
	}
}
