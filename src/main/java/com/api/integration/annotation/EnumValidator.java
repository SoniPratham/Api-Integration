package com.api.integration.annotation;

import java.lang.annotation.Documented;
/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : 19-Jun-2024
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidator {
	Class<? extends Enum<?>> enumClass();

	String message() default "must be a valid enum value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}