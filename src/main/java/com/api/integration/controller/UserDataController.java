package com.api.integration.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.integration.constant.Constant;
import com.api.integration.dto.UserDataFilterDTO;
import com.api.integration.entity.UserData;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.response.handler.GenericResponseHandlers;
import com.api.integration.service.UserDataService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@RestController
@RequestMapping("/user/data")
@RequiredArgsConstructor
@Slf4j
public class UserDataController {

	private final MessageByLocaleService messageByLocaleService;
	private final UserDataService userDataService;

	@PostMapping("/list")
	public ResponseEntity<Object> list(
			@RequestParam(defaultValue = Constant.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(defaultValue = Constant.PAGE_SIZE) Integer pageSize,
			@RequestBody UserDataFilterDTO filter) {

		log.info("Inside TempUserController::list -> filter {}", filter);

		Page<UserData> page = userDataService.list(pageNumber, pageSize, filter);

		log.info("Outside TempUserController::list");

		return new GenericResponseHandlers.Builder()
				.setStatus(HttpStatus.OK)
				.setMessage(messageByLocaleService.getMessage("user.data.list.message", null))
				.setData(page.getContent())
				.setHasNextPage(page.hasNext())
				.setHasPreviousPage(page.hasPrevious())
				.setTotalPages(page.getTotalPages())
				.setPageNumber(page.getNumber())
				.setTotalCount(page.getTotalElements())
				.setPageSize((long) page.getSize())
				.create();
	}

}
