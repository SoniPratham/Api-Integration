package com.api.integration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.integration.dto.ApiExecutionRequestDTO;
import com.api.integration.dto.GenericApiResponseDTO;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.response.handler.GenericResponseHandlers;
import com.api.integration.service.GenericApiCallerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/generic-api")
@RequiredArgsConstructor
@Slf4j
public class GenericApiCallerController {

	private final GenericApiCallerService apiCallerService;
	private final MessageByLocaleService messageByLocaleService;

	/**
	 * Generic Api calling
	 *
	 * @param  dto
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/call")
	public ResponseEntity<Object> call(@RequestBody @Valid ApiExecutionRequestDTO dto) throws Exception {

		log.info("Inside GenericApiCallerController::call -> dto {}", dto);

		GenericApiResponseDTO genericApiResponseDTO = apiCallerService.execute(dto);

		log.info("Outside GenericApiCallerController::call");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("generic.api.call.success", null))
				.setData(genericApiResponseDTO)
				.create();
	}
}
