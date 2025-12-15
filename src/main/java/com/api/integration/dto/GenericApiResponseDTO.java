package com.api.integration.dto;

import lombok.Data;

@Data
public class GenericApiResponseDTO {
	private boolean success;
	private Object rawResponse;
}
