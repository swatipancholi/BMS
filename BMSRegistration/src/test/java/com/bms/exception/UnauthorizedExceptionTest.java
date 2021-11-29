package com.bms.exception;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnauthorizedExceptionTest {

	@Test
	public void constructorTest() {
		UnauthorizedException unauthorizedException = new UnauthorizedException("Unauthorized");
		assertEquals("Unauthorized", unauthorizedException.getMessage());
	}
}
