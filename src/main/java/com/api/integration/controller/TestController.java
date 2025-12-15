package com.api.integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : 28-May-2024
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

	@GetMapping
	public String test() {
		return "Welcome to Api Integration Management System ";
	}

}
