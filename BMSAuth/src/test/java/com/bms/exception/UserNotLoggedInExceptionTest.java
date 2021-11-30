package com.bms.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserNotLoggedInExceptionTest {

	@Test
	 void constructortest()
	{
		UserNotLoggedInException userNotLoggedInException = new UserNotLoggedInException("Not Logged In");
		assertEquals("Not Logged In", userNotLoggedInException.getMessage());
	}
}
