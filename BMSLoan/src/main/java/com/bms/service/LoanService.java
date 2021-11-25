package com.bms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.model.LoanDetails;

@Service
public interface LoanService {
	public ResponseEntity<Object> apply(String token, LoanDetails loanDetails)
			throws UnauthorizedException, InvalidTokenException;

	public ResponseEntity<Object> getLoanDetails(String token, String username)
			throws UnauthorizedException, InvalidTokenException;
}
