package com.api.integration.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class ExternalSystemFilterDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7331300624885847305L;
	private String searchKeyword;
	private Boolean active;
}
