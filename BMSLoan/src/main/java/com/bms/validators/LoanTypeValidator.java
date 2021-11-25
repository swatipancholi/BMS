package com.bms.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bms.model.LoanDetails;

public class LoanTypeValidator implements ConstraintValidator<LoanType, String> {
	LoanDetails details = new LoanDetails();

	public final List<String> accountType = Arrays.asList("home", "vehicle", "gold", "personal", "business");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		return accountType.contains(value);
	}

}
