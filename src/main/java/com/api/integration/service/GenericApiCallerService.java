package com.api.integration.service;

import com.api.integration.dto.ApiExecutionRequestDTO;
import com.api.integration.dto.GenericApiResponseDTO;

public interface GenericApiCallerService {
	GenericApiResponseDTO execute(ApiExecutionRequestDTO request) throws Exception;
}
