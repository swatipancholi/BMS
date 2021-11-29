package com.bms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.exception.InitialDepositException;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.exception.UserNotLoggedInException;
import com.bms.model.Customer;
import com.bms.model.ResponseForSuccess;
@Service
public interface RegisterService {
	public ResponseEntity<ResponseForSuccess> register(Customer customer) 
			throws InitialDepositException,UnauthorizedException;
			
	public ResponseEntity<ResponseForSuccess> editDetails(String token,Customer customer,String cid) 
			throws UnauthorizedException,InvalidTokenException, UserNotLoggedInException;
	
	public ResponseEntity<Object> getCustomerDetails(String token, String customer_id)  
			throws UnauthorizedException,InvalidTokenException, UserNotLoggedInException;
}
