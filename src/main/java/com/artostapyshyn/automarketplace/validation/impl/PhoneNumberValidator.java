package com.artostapyshyn.automarketplace.validation.impl;

import com.artostapyshyn.automarketplace.repository.SellerRepository;
import com.artostapyshyn.automarketplace.validation.UniquePhoneNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
	
    private final SellerRepository sellerRepository;
    
    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return sellerRepository.findByPhoneNumber(phoneNumber) == null;
    }
}
