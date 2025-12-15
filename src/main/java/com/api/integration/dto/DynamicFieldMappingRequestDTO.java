package com.api.integration.dto;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DynamicFieldMappingRequestDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -608795098436058480L;

	@NotBlank(message = "{endpoint.uuid.not.null}")
	private String endpointUuid;

	@NotEmpty(message = "{mapping.list.not.empty}")
	private List<FieldMappingDTO> mappings;
}
