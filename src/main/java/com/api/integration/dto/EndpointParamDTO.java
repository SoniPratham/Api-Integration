package com.api.integration.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Data
public class EndpointParamDTO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6269799124025692592L;

	@NotBlank(message = "{param.key.not.null}")
	private String key;

	@NotBlank(message = "{param.value.not.null}")
	private String valueTemplate;
}
