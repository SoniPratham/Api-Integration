package com.api.integration.dto;

import java.io.Serializable;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApiExecutionRequestDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5843178834483019692L;

	@NotBlank(message = "{endpoint.uuid.not.null}")
	private String endpointUuid;

	// Optional custom param overrides while call api
	private Map<String, String> queryParams;
	private Map<String, String> pathParams;
}
