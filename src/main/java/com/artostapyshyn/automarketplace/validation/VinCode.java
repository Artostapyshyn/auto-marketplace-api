package com.artostapyshyn.automarketplace.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.artostapyshyn.automarketplace.validation.impl.VinCodeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = VinCodeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface VinCode {

    String message() default "Vin code is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
