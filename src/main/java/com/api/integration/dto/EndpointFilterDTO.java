package com.api.integration.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class EndpointFilterDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1123288804594612623L;
	private String endpointName;
	private String externalSystemUuid;
}
