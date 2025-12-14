package com.api.integration.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : 19-Jun-2024
 */

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, Object> {
	private Class<? extends Enum<?>> enumClass;

	@Override
	public void initialize(final EnumValidator annotation) {
		this.enumClass = annotation.enumClass();
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		if (value == null) {
			return true; // Allow null values, validation should focus on enum mismatch
		}

		Object[] enumValues = enumClass.getEnumConstants();
		for (Object enumValue : enumValues) {
			if (value.equals(enumValue.toString())) {
				return true;
			}
		}

		return false;
	}

}