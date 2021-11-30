package com.bms.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootTest
 class CustomizedExceptionHandlingTest {
	
	@InjectMocks
	CustomizedExceptionHandling customizedExceptionHandling;
	
	@Mock
	UnauthorizedException unauthorizedException;
	
	@Mock
	UsernameNotFoundException usernameNotFoundException;
	
	@Mock
	UserNotLoggedInException userNotLoggedInException;
	
	@Mock
	UserNameCanNotBeEmpty userNameCanNotBeEmpty;
	
	@Test
	 void handleUnauthorizedExceptionsTest()
	{
		ResponseEntity<?> responseEntity= customizedExceptionHandling.handleUnauthorizedExceptions(unauthorizedException);
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}
	
	@Test
	 void handleNullPointerExceptions()
	{
		ResponseEntity<?> responseEntity= customizedExceptionHandling.handleNullPointerExceptions(null);
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}
	
	@Test
	 void handleUserNameNotFoundException()
	{
		ResponseEntity<?> responseEntity= customizedExceptionHandling.usernameNotFoundExceptions(usernameNotFoundException);
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}

	@Test
	void handleUserNameCanNotBeEmptyException()
	{
		ResponseEntity<?> responseEntity= customizedExceptionHandling.userNameCanNotBeEmpty(userNameCanNotBeEmpty);
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}
	
	@Test
	void handleUserNameLoggedInException()
	{
		ResponseEntity<?> responseEntity= customizedExceptionHandling.userNotLoggedInException(userNotLoggedInException);
		assertEquals(400, responseEntity.getStatusCodeValue());
		
	}
}
