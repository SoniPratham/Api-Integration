package com.api.integration.dto;

import java.io.Serializable;
import java.util.List;

import com.api.integration.annotation.EnumValidator;
import com.api.integration.enums.HttpMethodType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class EndpointRequestDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3872618414525157105L;

	private String uuid;

	@NotBlank(message = "{endpoint.name.not.null}")
	private String endpointName;

	@NotBlank(message = "{endpoint.path.not.null}")
	private String path;

	private String jsonRootNode;

	@NotBlank(message = "{endpoint.http.method.not.null}")
	@EnumValidator(enumClass = HttpMethodType.class, message = "{http.method.invalid}")
	private String httpMethod;

	private String requestBodyTemplate;

	@NotBlank(message = "{external.system.uuid.not.null}")
	private String externalSystemUuid;

	private List<@Valid EndpointParamDTO> queryParams;

	private List<@Valid EndpointParamDTO> pathParams;
}
