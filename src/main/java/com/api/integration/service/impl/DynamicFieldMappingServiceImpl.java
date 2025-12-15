package com.api.integration.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.integration.dto.DynamicFieldMappingRequestDTO;
import com.api.integration.dto.FieldMappingDTO;
import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.exception.NotFoundException;
import com.api.integration.mapper.DynamicFieldMappingMapper;
import com.api.integration.repo.DynamicFieldMappingRepository;
import com.api.integration.repo.EndpointConfigurationRepository;
import com.api.integration.service.DynamicFieldMappingService;
import com.api.integration.utils.CommonUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicFieldMappingServiceImpl implements DynamicFieldMappingService {

	private final EndpointConfigurationRepository endpointRepository;
	private final DynamicFieldMappingRepository mappingRepository;
	private final DynamicFieldMappingMapper mapper;

	@Override
	public void saveOrUpdateMappings(DynamicFieldMappingRequestDTO dto)
			throws NotFoundException {

		log.info("Inside DynamicFieldMappingServiceImpl::saveOrUpdateMappings -> dto {}", dto);

		EndpointConfiguration endpoint = endpointRepository.findByUuid(dto.getEndpointUuid())
				.orElseThrow(() -> new NotFoundException("endpoint.not.found"));

		/**
		 * Fetch existing mappings
		 */
		List<DynamicFieldMapping> existingMappings = mappingRepository.findByEndpointUuid(dto.getEndpointUuid());

		Map<String, DynamicFieldMapping> existingMapByUuid = existingMappings.stream()
				.filter(m -> m.getUuid() != null)
				.collect(Collectors.toMap(DynamicFieldMapping::getUuid, m -> m));

		// Collect UUIDs from incoming request
		Set<String> incomingUuids = dto.getMappings().stream()
				.map(FieldMappingDTO::getUuid)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		/**
		 * CREATE or UPDATE
		 */
		for (FieldMappingDTO mappingDTO : dto.getMappings()) {

			if (mappingDTO.getUuid() == null) {
				// CREATE
				DynamicFieldMapping newEntity = mapper.toEntity(mappingDTO, endpoint);
				newEntity.setActive(Boolean.TRUE);
				newEntity.setUuid(CommonUtility.generateUuid(mappingRepository));
				newEntity.setEndpoint(endpoint);
				mappingRepository.save(newEntity);

			} else {
				// UPDATE
				DynamicFieldMapping existing = existingMapByUuid.get(mappingDTO.getUuid());
				if (existing == null) {
					throw new NotFoundException("field.mapping.not.found");
				}
				existing.setId(existing.getId());
				existing.setActive(existing.getActive());
				existing.setUuid(existing.getUuid());

				mapper.updateEntity(existing, mappingDTO);
				mappingRepository.save(existing);
			}
		}

		/**
		 * DELETE missing mappings
		 */
		for (DynamicFieldMapping oldMapping : existingMappings) {
			if (oldMapping.getUuid() != null && !incomingUuids.contains(oldMapping.getUuid())) {
				mappingRepository.delete(oldMapping);
			}
		}

		log.info("Outside DynamicFieldMappingServiceImpl::saveOrUpdateMappings");
	}

	@Override
	public List<DynamicFieldMapping> getMappingsByEndpoint(String endpointUuid)
			throws NotFoundException {

		log.info("Inside DynamicFieldMappingServiceImpl::getMappingsByEndpoint {}", endpointUuid);

		// Validate endpoint exists
		endpointRepository.findByUuid(endpointUuid)
				.orElseThrow(() -> new NotFoundException("endpoint.not.found"));

		List<DynamicFieldMapping> list = mappingRepository.findByEndpointUuid(endpointUuid);

		log.info("Outside DynamicFieldMappingServiceImpl::getMappingsByEndpoint");
		return list;
	}
}
