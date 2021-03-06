package com.bms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.dao.LoanDetailsDao;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNotLoggedInException;
import com.bms.model.LoanDetails;
import com.bms.model.ResponseForSuccess;
import com.bms.model.ValidateToken;
import com.bms.restclients.AuthFeign;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDetailsDao loanDetailsDao;

	@Autowired
	private AuthFeign authFeign;

	
	@Override
	public ResponseEntity<Object> apply(String token, LoanDetails loanDetails)
			throws UnauthorizedException, InvalidTokenException, UserNotLoggedInException {
		ValidateToken validate = authFeign.getValidity(token).getBody();
		if (validate.isValid()) {
			if (!validate.isLoggedIn()) {
				throw new UserNotLoggedInException("User Not Logged In. Please login again");
			}
			loanDetails.setUsername(validate.getUsername());
			loanDetails.setDate(new Date());
			loanDetailsDao.save(loanDetails);
			return new ResponseEntity<>(new ResponseForSuccess("Loan Applied Successfully",
					loanDetails.getUsername().toString(), "/loan/apply"), HttpStatus.OK);
		} else {
			throw new InvalidTokenException("Token not valid");
		}

	}

	@Override
	public ResponseEntity<Object> getLoanDetails(String token, String username)
			throws UnauthorizedException, InvalidTokenException, UserNotLoggedInException {
		ValidateToken validate = authFeign.getValidity(token).getBody();
		if (validate.isValid()) {
			List<LoanDetails> loanDetails = loanDetailsDao.findAllByUsername(username);
			if (!loanDetails.isEmpty()) {
				if (!validate.isLoggedIn()) {
					throw new UserNotLoggedInException("User Not Logged In. Please login again");
				}
				return new ResponseEntity<>(loanDetails, HttpStatus.OK);
			} else {
				throw new UnauthorizedException("Loan Details Not Found");
			}
		} else {
			throw new InvalidTokenException("Token not valid");
		}
	}

}
