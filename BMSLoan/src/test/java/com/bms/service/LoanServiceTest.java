package com.bms.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bms.dao.LoanDetailsDao;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNotLoggedInException;
import com.bms.model.LoanDetails;
import com.bms.model.ValidateToken;
import com.bms.restclients.AuthFeign;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

public class LoanServiceTest {

	@InjectMocks
	LoanServiceImpl loanService;
	
	@Mock
	LoanDetailsDao loanDetailsDao;
	
	private LoanDetails loanDetail;
	private ValidateToken validateToken;
	private ResponseEntity<ValidateToken> response;
	
	@Mock
	AuthFeign authFeign;
	
	@BeforeAll
	public void init() {
		validateToken = new ValidateToken("uid","name",true,true);
		response = new ResponseEntity<ValidateToken>(validateToken,HttpStatus.OK);
	}
	
	@Test
	public void applyLoanTest() throws UnauthorizedException, InvalidTokenException, UserNotLoggedInException {
		loanDetail = new LoanDetails();
		loanDetail.setLoanAmount(40000);
		loanDetail.setDuration(5);
		loanDetail.setLoanType("personal");
		loanDetail.setDate(new Date());
		loanDetail.setRateOfInterest(6);
		loanDetail.setUsername("swati");
		validateToken = new ValidateToken("uid","name",true,true);
		response = new ResponseEntity<ValidateToken>(validateToken,HttpStatus.OK);
		when(authFeign.getValidity("token")).thenReturn(response);
		when(loanDetailsDao.save(loanDetail)).thenReturn(loanDetail);
		assertEquals(200, loanService.apply("token", loanDetail));
	}
}
