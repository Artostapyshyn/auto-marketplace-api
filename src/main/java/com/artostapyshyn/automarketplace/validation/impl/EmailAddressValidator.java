package com.artostapyshyn.automarketplace.validation.impl;

import com.artostapyshyn.automarketplace.repository.SellerRepository;
import com.artostapyshyn.automarketplace.validation.UniqueEmailAddress;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailAddressValidator implements ConstraintValidator<UniqueEmailAddress, String> {
	
	private final SellerRepository sellerRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return sellerRepository.findByEmail(email) == null;
    }
}
