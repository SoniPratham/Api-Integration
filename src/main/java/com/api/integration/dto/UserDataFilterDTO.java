package com.api.integration.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDataFilterDTO {

	private String searchKeyword;
	private LocalDate startDate;
	private LocalDate endDate;
}
