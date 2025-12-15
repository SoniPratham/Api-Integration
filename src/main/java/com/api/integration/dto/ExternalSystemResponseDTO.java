package com.api.integration.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class ExternalSystemResponseDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6367067305220806464L;
	private String uuid;
	private String name;
	private String baseUrl;
	private String authType;
	private Boolean active;
}
