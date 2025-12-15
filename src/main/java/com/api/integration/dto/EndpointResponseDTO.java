package com.api.integration.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class EndpointResponseDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4806092158283463279L;
	private String uuid;
	private String endpointName;
	private String path;
	private String httpMethod;
	private String requestBodyTemplate;
	private String externalSystemUuid;
}
