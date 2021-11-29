package com.bms.model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class LoanDetailsTest {

	@InjectMocks
	LoanDetails loanDetail;
	
	@Before
	public void init() {
		loanDetail = new LoanDetails();
		loanDetail.setLoanAmount(40000);
		loanDetail.setDuration(5);
		loanDetail.setLoanType("personal");
		loanDetail.setDate(new Date());
		loanDetail.setRateOfInterest(6);
		loanDetail.setUsername("swati");
	}
	
	@Test
	public void testUsername() {
		assertEquals("swati", loanDetail.getUsername());
	}
	
	@Test
	public void testLoanAmount() {
		assertEquals(40000, loanDetail.getLoanAmount());
	}
	
	@Test
	public void testLoanType() {
		assertEquals("personal", loanDetail.getLoanType());
	}
	
	@Test
	public void testDuration() {
		assertEquals(5, loanDetail.getDuration());
	}
	
	@Test
	public void testRateOfInterest() {
		assertEquals(6, loanDetail.getRateOfInterest(), 0);
	}
	
	@Test
	public void testDate() {
		assertEquals(new Date(), loanDetail.getDate());
	}
}
