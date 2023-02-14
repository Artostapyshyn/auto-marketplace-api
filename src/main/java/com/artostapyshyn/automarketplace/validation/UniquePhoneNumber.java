package com.artostapyshyn.automarketplace.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.artostapyshyn.automarketplace.validation.impl.PhoneNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNumber {

    String message() default "This phone number is already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}