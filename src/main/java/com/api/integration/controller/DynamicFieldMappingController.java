package com.api.integration.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.integration.dto.DynamicFieldMappingRequestDTO;
import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.exception.NotFoundException;
import com.api.integration.exception.ValidationException;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.mapper.DynamicFieldMappingMapper;
import com.api.integration.response.handler.GenericResponseHandlers;
import com.api.integration.service.DynamicFieldMappingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dynamic-field-mapping")
@RequiredArgsConstructor
@Slf4j
public class DynamicFieldMappingController {

	private final DynamicFieldMappingService dynamicFieldMappingService;
	private final DynamicFieldMappingMapper mapper;
	private final MessageByLocaleService messageByLocaleService;

	@PostMapping("/save")
	public ResponseEntity<Object> save(@RequestBody @Valid DynamicFieldMappingRequestDTO dto)
			throws NotFoundException, ValidationException {

		log.info("Inside DynamicFieldMappingController::save -> dto {}", dto);

		dynamicFieldMappingService.saveOrUpdateMappings(dto);

		log.info("Outside DynamicFieldMappingController::save");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("dynamic.field.mapping.save.message", null))
				.create();
	}

	@GetMapping("/list/{endpointUuid}")
	public ResponseEntity<Object> list(@PathVariable String endpointUuid) throws NotFoundException {

		log.info("Inside DynamicFieldMappingController::list -> endpointUuid {}", endpointUuid);

		List<DynamicFieldMapping> list = dynamicFieldMappingService.getMappingsByEndpoint(endpointUuid);

		log.info("Outside DynamicFieldMappingController::list");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("dynamic.field.mapping.list.message", null))
				.setData(mapper.toResponseDTOs(list))
				.create();
	}
}
