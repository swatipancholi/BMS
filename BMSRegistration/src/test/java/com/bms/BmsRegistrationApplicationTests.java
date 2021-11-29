package com.bms;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BmsRegistrationApplicationTests {

	@Test
	void contextLoads() {
		BmsRegistrationApplication.main(new String[] {});
		assertTrue(true);
	}

}
