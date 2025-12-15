package com.api.integration.service;

import java.util.List;

import com.api.integration.dto.DynamicFieldMappingRequestDTO;
import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.exception.NotFoundException;
import com.api.integration.exception.ValidationException;

public interface DynamicFieldMappingService {

	/**
	 *
	 * @param  dto
	 * @throws NotFoundException
	 * @throws ValidationException
	 */
	void saveOrUpdateMappings(DynamicFieldMappingRequestDTO dto)
			throws NotFoundException, ValidationException;

	/**
	 *
	 * @param  endpointUuid
	 * @return
	 * @throws NotFoundException
	 */
	List<DynamicFieldMapping> getMappingsByEndpoint(String endpointUuid)
			throws NotFoundException;
}
