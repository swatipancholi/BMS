package com.bms.exception;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserNotLoggedInExceptionTest {
	@Test
	public void constructorTest() {
		UserNotLoggedInException userNotLoggedInException = new UserNotLoggedInException("User not logged in");
		assertEquals("User not logged in", userNotLoggedInException.getMessage());
	}
}
