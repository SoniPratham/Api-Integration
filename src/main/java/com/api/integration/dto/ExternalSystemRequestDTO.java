package com.api.integration.dto;

import java.io.Serializable;

import com.api.integration.annotation.EnumValidator;
import com.api.integration.constant.Constant;
import com.api.integration.enums.AuthType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class ExternalSystemRequestDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4006422707054476814L;

	private String uuid;

	@NotBlank(message = "{external.system.name.not.null}")
	@Size(max = 255, message = Constant.FIELD_SIZE_EXCEED_MESSAGE_255)
	private String name;

	@NotBlank(message = "{external.system.base.url.not.null}")
	@Size(max = 500, message = Constant.FIELD_SIZE_EXCEED_MESSAGE_255)
	private String baseUrl;

	@NotBlank(message = "{external.system.auth.type.not.null}")
	@EnumValidator(enumClass = AuthType.class, message = "{external.system.auth.type.invalid}")
	private String authType;

	@NotBlank(message = "{external.system.auth.value.not.null}")
	private String authValue;

}
