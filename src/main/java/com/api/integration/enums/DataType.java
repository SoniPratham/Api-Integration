package com.api.integration.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {

	STRING("STRING"),
	NUMBER("NUMBER"),
	BOOLEAN("BOOLEAN"),
	DATE("DATE");

	private final String type;

	private static final Map<String, DataType> MAP = new HashMap<>();

	static {
		for (DataType value : values()) {
			MAP.put(value.getType(), value);
		}
	}

	public static DataType getByValue(String value) {
		return MAP.get(value);
	}
}
