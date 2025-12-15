package com.api.integration.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.UserData;
import com.api.integration.enums.DataType;
import com.api.integration.exception.ValidationException;
import com.api.integration.repo.DynamicFieldMappingRepository;
import com.api.integration.repo.UserDataRepository;
import com.api.integration.service.ResponseParsingService;
import com.api.integration.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResponseParsingServiceImpl implements ResponseParsingService {

	private final DynamicFieldMappingRepository mappingRepository;
	private final UserDataRepository userDataRepository;

	@Override
	public List<UserData> parseAndConvert(Object response, EndpointConfiguration endpoint) throws Exception {

		log.info("Inside ResponseParsingServiceImpl::parseAndConvert");

		JsonNode rootNode = JsonUtils.toJsonNode(response);

		// Calendly: users inside "collection"
		JsonNode arrayNode = rootNode.get("collection");

		if (arrayNode == null || !arrayNode.isArray()) {
			throw new ValidationException("invalid.external.response");
		}

		List<DynamicFieldMapping> mappings = mappingRepository.findByEndpoint(endpoint);

		List<UserData> results = new ArrayList<>();

		for (JsonNode item : arrayNode) {
			UserData user = new UserData();

			for (DynamicFieldMapping mapping : mappings) {
				JsonNode value = item.at(mapping.getExternalField());

				Object finalVal = convertByType(value, mapping.getDataType());

				Field field = UserData.class.getDeclaredField(mapping.getInternalField());
				field.setAccessible(true);
				field.set(user, finalVal);
			}

			userDataRepository.save(user);
			results.add(user);
		}

		log.info("Outside ResponseParsingServiceImpl::parseAndConvert");

		return results;
	}

	private Object convertByType(JsonNode value, DataType type) {

		if (value == null || value.isNull()) {
			return null;
		}

		return switch (type) {
		case STRING -> value.asText();
		case NUMBER -> value.asDouble();
		case BOOLEAN -> value.asBoolean();
		case DATE -> LocalDate.parse(value.asText());
		};
	}
}
