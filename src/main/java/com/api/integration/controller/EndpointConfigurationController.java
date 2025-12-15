package com.api.integration.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.integration.constant.Constant;
import com.api.integration.dto.EndpointFilterDTO;
import com.api.integration.dto.EndpointRequestDTO;
import com.api.integration.dto.EndpointResponseDTO;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.exception.NotFoundException;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.mapper.EndpointConfigurationMapper;
import com.api.integration.response.handler.GenericResponseHandlers;
import com.api.integration.service.EndpointConfigurationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@RestController
@RequestMapping("/endpoint/config")
@RequiredArgsConstructor
@Slf4j
public class EndpointConfigurationController {

	private final EndpointConfigurationService endpointConfigurationService;
	private final MessageByLocaleService messageByLocaleService;
	private final EndpointConfigurationMapper endpointConfigurationMapper;

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody @Valid EndpointRequestDTO dto) throws NotFoundException {

		log.info("Inside EndpointConfigurationController::add -> dto {}", dto);

		EndpointResponseDTO response = endpointConfigurationService.add(dto);

		log.info("Outside EndpointConfigurationController::add");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("endpoint.add.message", null))
				.setData(response)
				.create();
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody @Valid EndpointRequestDTO dto) throws NotFoundException {

		log.info("Inside EndpointConfigurationController::update -> dto {}", dto);

		EndpointResponseDTO response = endpointConfigurationService.update(dto);

		log.info("Outside EndpointConfigurationController::update");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("endpoint.update.message", null))
				.setData(response)
				.create();
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<Object> details(@PathVariable String uuid) throws NotFoundException {

		log.info("Inside EndpointConfigurationController::details -> uuid {}", uuid);

		EndpointResponseDTO response = endpointConfigurationService.getDetails(uuid);

		log.info("Outside EndpointConfigurationController::details");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("endpoint.detail.message", null))
				.setData(response)
				.create();
	}

	@PostMapping("/list")
	public ResponseEntity<Object> list(
			@RequestParam(defaultValue = Constant.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = Constant.PAGE_SIZE) Integer pageSize,
			@RequestBody EndpointFilterDTO filter) {

		log.info("Inside EndpointConfigurationController::list -> filter {}", filter);

		Page<EndpointConfiguration> page = endpointConfigurationService.getList(pageNumber, pageSize, filter);

		log.info("Outside EndpointConfigurationController::list");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("endpoint.list.message", null))
				.setData(endpointConfigurationMapper.toResponseDTOs(page.getContent()))
				.setHasNextPage(page.hasNext())
				.setHasPreviousPage(page.hasPrevious())
				.setTotalPages(page.getTotalPages())
				.setPageNumber(page.getNumber())
				.setTotalCount(page.getTotalElements())
				.setPageSize((long) page.getSize())
				.create();
	}
}
