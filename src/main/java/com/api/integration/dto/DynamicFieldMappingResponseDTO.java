package com.api.integration.dto;

import lombok.Data;

@Data
public class DynamicFieldMappingResponseDTO {

	private String uuid;
	private String externalField;
	private String internalField;
	private String dataType;
	private String endpointUuid;
}
