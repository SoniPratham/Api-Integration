package com.api.integration.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Getter
@AllArgsConstructor
public enum HttpMethodType {

	GET("GET"),
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	PATCH("PATCH");

	private final String method;

	private static final Map<String, HttpMethodType> METHOD_MAP = new HashMap<>();

	static {
		for (HttpMethodType type : values()) {
			METHOD_MAP.put(type.getMethod(), type);
		}
	}

	public static HttpMethodType getByValue(String value) {
		return METHOD_MAP.get(value);
	}
}
