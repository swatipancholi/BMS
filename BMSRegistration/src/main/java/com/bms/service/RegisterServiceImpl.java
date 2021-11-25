package com.bms.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bms.dao.CustomerDao;
import com.bms.dao.CustomerDataDao;
import com.bms.exception.InitialDepositException;
import com.bms.exception.InvalidTokenException;
import com.bms.exception.UnauthorizedException;
import com.bms.model.Customer;
import com.bms.model.CustomerData;
import com.bms.model.ResponseForSuccess;
import com.bms.restclients.AuthFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RegisterServiceImpl implements RegisterService {

	
	@Autowired
	private CustomerDao cd;
	
	@Autowired
	private CustomerDataDao cdao;
	
	@Autowired
	private AuthFeign authFeign;

	@Override
	public ResponseEntity<ResponseForSuccess> register(Customer customer) throws InitialDepositException,UnauthorizedException{
		
			Optional<CustomerData> user=cdao.findById(customer.getLoginDetails().getUsername());
			if(user.isPresent()) {
				throw new UnauthorizedException("User already Exists");
			}
			else {
				customer.setCustomer_id("R-"+ (101 + cd.count()) );
				
				cd.save(customer);
				return new ResponseEntity<>(new ResponseForSuccess("User Registered Successfully ",customer.getCustomer_id(),"/customer/"+customer.getCustomer_id()+"/getDetails"), HttpStatus.OK);
			}
		}
	
	@Override
	public ResponseEntity<ResponseForSuccess> editDetails(String token,Customer customer,String cid) throws UnauthorizedException,InvalidTokenException{
		
		if (authFeign.getValidity(token).getBody().isValid()) {
				Optional<Customer> cust=cd.findById(cid);
				if(cust.isPresent()) {
					customer.setCustomer_id(cid);
					cd.save(customer);
					return new ResponseEntity<>(new ResponseForSuccess("Customer Details Updated Successfully",customer.getCustomer_id(),"/customer/"+customer.getCustomer_id()+"/getDetails"), HttpStatus.OK);
				}
				else {
					throw new UnauthorizedException("User Not Found");
				}
		}
		else {
			throw new InvalidTokenException("Token not valid");
		}
	}
	
	
	@Override
	public ResponseEntity<Object> getCustomerDetails(String cid) {
		Optional<Customer> cust=cd.findById(cid);
		if(cust.isPresent()) {
			return new ResponseEntity<>(cust.get(),HttpStatus.OK);
		}
		else {
			throw new UnauthorizedException("User Not Found");
		}
	}
	


}
