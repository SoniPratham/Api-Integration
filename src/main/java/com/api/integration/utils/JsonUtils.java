package com.api.integration.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private JsonUtils() {
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static JsonNode toJsonNode(Object obj) {
		return objectMapper.valueToTree(obj);
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert JSON to object: " + clazz.getSimpleName(), e);
		}
	}
}
