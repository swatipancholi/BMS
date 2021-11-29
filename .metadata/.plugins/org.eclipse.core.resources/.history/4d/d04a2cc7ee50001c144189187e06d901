
package com.bms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AuthResponseTest {

	AuthResponse auth = new AuthResponse();
	AuthResponse auth1 = new AuthResponse("axel", "axel@123", true, true);

	@Test
	void testUsername() {
		auth.setUsername("axel");
		assertEquals( "axel", auth.getUsername());
	}

	@Test
	void testName() {
		auth.setMessage("Name");
		assertEquals("Name", auth.getMessage());
	}

	@Test
	void testIsValid() {
		auth.setValid(true);
		assertEquals( true, auth.isValid());
	}
	
	@Test
	void testIsLoggedIn() {
		auth.setLoggedIn(true);
		assertEquals( true, auth.isValid());
	}

}
