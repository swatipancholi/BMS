package com.bms;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BmsLoanApplicationTests {

	@Test
	void contextLoads() {
		BmsLoanApplication.main(new String[] {});
		assertTrue(true);
	}

}
