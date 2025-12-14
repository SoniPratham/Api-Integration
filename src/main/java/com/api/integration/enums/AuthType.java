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
public enum AuthType {

	BEARER("BEARER"),
	API_KEY("API_KEY"),
	OAUTH("OAUTH");

	private final String authType;

	private static final Map<String, AuthType> AUTH_TYPE_LIST = new HashMap<>();

	static {
		for (final AuthType type : values()) {
			AUTH_TYPE_LIST.put(type.getAuthType(), type);
		}
	}

	public static AuthType getByValue(final String value) {
		return AUTH_TYPE_LIST.get(value);
	}
}
