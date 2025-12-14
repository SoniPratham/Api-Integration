package com.api.integration.dto;

import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class ExternalSystemResponseDTO {
	private String uuid;
	private String name;
	private String baseUrl;
	private String authType;
	private Boolean active;
}
