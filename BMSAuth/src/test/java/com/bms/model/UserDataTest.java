
package com.bms.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserDataTest {

	CustomerData auth = new CustomerData();
	CustomerData auth1 = new CustomerData("swati", "1234swati", "token");

	@Test
	void testUsername() {
		auth.setUsername("swati");
		assertEquals( "swati", auth.getUsername());
	}

	@Test
	void testName() {
		auth.setPassword("Name");
		assertEquals( "Name", auth.getPassword());
	}

	
	@Test
	void testToken() {
		auth.setAuthToken("ad");
		assertEquals("ad", auth.getAuthToken());
	}
	
	@Test
	void testToString() {
		String string = auth1.toString();
		assertEquals(auth1.toString(),string);
	}

}
