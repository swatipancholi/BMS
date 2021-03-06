package com.bms.service;

import java.util.Base64;
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
import com.bms.exception.UserNotLoggedInException;
import com.bms.model.Customer;
import com.bms.model.CustomerData;
import com.bms.model.ResponseForSuccess;
import com.bms.restclients.AuthFeign;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private CustomerDao cd;

	@Autowired
	private CustomerDataDao cdao;

	@Autowired
	private AuthFeign authFeign;

	@Override
	public ResponseEntity<ResponseForSuccess> register(Customer customer)
			throws InitialDepositException, UnauthorizedException,NullPointerException {
		
        if(customer.getState().isEmpty()|| customer.getContactNo().isEmpty()||customer.getEmailAddress().isEmpty()
        		||customer.getCountry().isEmpty()) {
        	throw new NullPointerException("Values can not be empty");
        }
		Optional<CustomerData> user = cdao.findById(customer.getLoginDetails().getUsername());
		if (user.isPresent()) {
			throw new UnauthorizedException("User already Exists");
		} else {
			customer.setCustomer_id("R-" + (101 + cd.count()));
			CustomerData customerData = customer.getLoginDetails();
			String customerEncodedPassword = Base64.getEncoder().encodeToString(customerData.getPassword().getBytes());
			customerData.setPassword(customerEncodedPassword);
			customer.setLoginDetails(customerData);
			cd.save(customer);
			return new ResponseEntity<>(new ResponseForSuccess("User Registered Successfully ",
					customer.getCustomer_id(), "/customer/" + customer.getCustomer_id() + "/getDetails"),
					HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseForSuccess> editDetails(String token, Customer customer, String cid)
			throws UnauthorizedException, InvalidTokenException, UserNotLoggedInException {

		if (authFeign.getValidity(token).getBody().isValid()) {
			if (!authFeign.getValidity(token).getBody().isLoggedIn()) {
				throw new UserNotLoggedInException("User Not Logged-in. Please Login first");
			}
			Optional<Customer> cust = cd.findById(cid);
			if (cust.isPresent()) {
				Optional<CustomerData> user = cdao.findById(customer.getLoginDetails().getUsername());

				Boolean userNameChanged = !(cust.get().getLoginDetails().getUsername()
						.equals(customer.getLoginDetails().getUsername()));

				if (userNameChanged && user.isPresent()) {
					throw new UnauthorizedException("User Name already Exists");
				}

				CustomerData customerData = customer.getLoginDetails();
				String customerEncodedPassword = Base64.getEncoder()
						.encodeToString(customerData.getPassword().getBytes());
				customerData.setPassword(customerEncodedPassword);
				customer.setLoginDetails(customerData);

				customer.setCustomer_id(cid);
				String previousUserName = cust.get().getLoginDetails().getUsername();
				cd.save(customer);
				System.out.print(previousUserName);
				cdao.deleteById(previousUserName);
				return new ResponseEntity<>(new ResponseForSuccess("Customer Details Updated Successfully",
						customer.getCustomer_id(), "/customer/" + customer.getCustomer_id() + "/getDetails"),
						HttpStatus.OK);
			} else {
				throw new UnauthorizedException("User Not Found");
			}
		} else {
			throw new InvalidTokenException("Token not valid");
		}
	}

	@Override
	public ResponseEntity<Object> getCustomerDetails(String token, String cid)
			throws UnauthorizedException, InvalidTokenException, UserNotLoggedInException {
		if (authFeign.getValidity(token).getBody().isValid()) {
			Optional<Customer> cust = cd.findById(cid);
			if (cust.isPresent()) {
				if (!authFeign.getValidity(token).getBody().isLoggedIn()) {
					throw new UserNotLoggedInException("User Not Logged-in. Please Login first");
				}

				byte[] actualByte = Base64.getDecoder().decode(cust.get().getLoginDetails().getPassword());
				String userDecodedPassword = new String(actualByte);
				CustomerData customerData = cust.get().getLoginDetails();
				customerData.setPassword(userDecodedPassword);
				cust.get().setLoginDetails(customerData);
				return new ResponseEntity<>(cust.get(), HttpStatus.OK);
			} else {
				throw new UnauthorizedException("User Not Found");
			}
		} else {
			throw new InvalidTokenException("Token not valid");
		}
	}

}
