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
import com.api.integration.dto.ExternalSystemFilterDTO;
import com.api.integration.dto.ExternalSystemRequestDTO;
import com.api.integration.dto.ExternalSystemResponseDTO;
import com.api.integration.entity.ExternalSystem;
import com.api.integration.exception.NotFoundException;
import com.api.integration.exception.ValidationException;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.mapper.ExternalSystemMapper;
import com.api.integration.response.handler.GenericResponseHandlers;
import com.api.integration.service.ExternalSystemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@RestController
@RequestMapping("/external/system")
@RequiredArgsConstructor
@Slf4j
public class ExternalSystemController {

	private final ExternalSystemService externalSystemService;
	private final MessageByLocaleService messageByLocaleService;
	private final ExternalSystemMapper externalSystemMapper;

	@PostMapping
	public ResponseEntity<Object> add(@RequestBody @Valid ExternalSystemRequestDTO dto) throws ValidationException {
		log.info("Inside ExternalSystemController::add -> dto {}", dto);
		ExternalSystemResponseDTO response = externalSystemService.addExternalSystem(dto);
		log.info("Outside ExternalSystemController::add");
		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("external.system.create.message", null))
				.setData(response)
				.create();
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody @Valid ExternalSystemRequestDTO dto)
			throws ValidationException, NotFoundException {
		log.info("Inside ExternalSystemController::update -> dto {}", dto);
		externalSystemService.updateExternalSystem(dto);
		log.info("Outside ExternalSystemController::update");
		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("external.system.update.message", null))
				.create();
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<Object> get(@PathVariable String uuid)
			throws NotFoundException {
		log.info("Inside ExternalSystemController::get -> uuid {}", uuid);
		ExternalSystemResponseDTO dto = externalSystemService.getExternalSystemDetails(uuid);
		log.info("Outside ExternalSystemController::get");
		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("external.system.detail.message", null))
				.setData(dto)
				.create();
	}

	@PostMapping("/list")
	public ResponseEntity<Object> list(
			@RequestParam(defaultValue = Constant.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = Constant.PAGE_SIZE) Integer pageSize,
			@RequestBody ExternalSystemFilterDTO filter) {

		log.info("Inside ExternalSystemController::list -> filter {}", filter);

		Page<ExternalSystem> page = externalSystemService.getList(pageNumber, pageSize, filter);

		log.info("Outside ExternalSystemController::list");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("external.system.list.message", null))
				.setData(externalSystemMapper.toResponseDTOs(page.getContent()))
				.setHasNextPage(page.hasNext())
				.setHasPreviousPage(page.hasPrevious())
				.setTotalPages(page.getTotalPages())
				.setPageNumber(page.getNumber())
				.setTotalCount(page.getTotalElements())
				.setPageSize((long) page.getSize())
				.create();
	}

	@PutMapping("/status/{uuid}")
	public ResponseEntity<Object> changeStatus(
			@PathVariable String uuid,
			@RequestParam Boolean active)
			throws ValidationException, NotFoundException {

		log.info("Inside ExternalSystemController::changeStatus -> uuid {}, active {}", uuid, active);

		externalSystemService.changeStatus(uuid, active);

		log.info("Outside ExternalSystemController::changeStatus");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("external.system.update.message", null))
				.create();
	}
}
