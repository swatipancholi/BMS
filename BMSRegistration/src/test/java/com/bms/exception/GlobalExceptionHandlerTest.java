package com.bms.exception;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class GlobalExceptionHandlerTest {

	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Test
	public void invalidTokenExceptionTest() {
		ResponseEntity<?> responseEntity = globalExceptionHandler.invalidTokenException(new InvalidTokenException("Invalid Token"));
		assertEquals(401, responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void userNotLoggedInExceptionTest() {
		ResponseEntity<?> responseEntity = globalExceptionHandler.userNotLoggedInException(new UserNotLoggedInException("Not logged in"));
		assertEquals(401, responseEntity.getStatusCodeValue());
	}
	
}
