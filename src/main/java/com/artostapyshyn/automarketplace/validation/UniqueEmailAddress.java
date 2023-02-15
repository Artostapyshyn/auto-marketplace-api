package com.artostapyshyn.automarketplace.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.artostapyshyn.automarketplace.validation.impl.EmailAddressValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EmailAddressValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailAddress {

    String message() default "Email is already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
