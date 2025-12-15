package com.api.integration.dto;

import java.io.Serializable;

import com.api.integration.annotation.EnumValidator;
import com.api.integration.enums.DataType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FieldMappingDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3464004337373063868L;

	private String uuid; // optional for update

	@NotBlank(message = "{external.field.not.null}")
	private String externalField;

	@NotBlank(message = "{internal.field.not.null}")
	private String internalField;

	@NotBlank(message = "{data.type.not.null}")
	@EnumValidator(enumClass = DataType.class, message = "{data.type.invalid}")
	private String dataType;
}
