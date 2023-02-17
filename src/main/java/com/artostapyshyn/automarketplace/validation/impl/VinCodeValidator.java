package com.artostapyshyn.automarketplace.validation.impl;

import java.util.regex.Pattern;

import com.artostapyshyn.automarketplace.validation.VinCode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VinCodeValidator implements ConstraintValidator<VinCode, String>{

	 private static final String REGEX_STRING = "[(A-H|J-N|P|R-Z|0-9)]{17}";
	    
	    @Override
	    public boolean isValid(String vinCode, ConstraintValidatorContext constraintValidatorContext) {
	        return Pattern.compile(REGEX_STRING).matcher(vinCode).matches();
	    }

}
