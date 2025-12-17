package com.api.integration.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.UserData;
import com.api.integration.enums.DataType;
import com.api.integration.exception.ValidationException;
import com.api.integration.repo.DynamicFieldMappingRepository;
import com.api.integration.repo.UserDataRepository;
import com.api.integration.service.ResponseParsingService;
import com.api.integration.utils.CommonUtility;
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
	public List<UserData> parseAndConvert(Object response, EndpointConfiguration endpoint)
			throws ValidationException {

		log.info("Inside ResponseParsingServiceImpl::parseAndConvert");

		JsonNode rootNode = JsonUtils.toJsonNode(response);

		/**
		 * If jsonRootNode is present (ex: "collection") move inside that node
		 */
		if (StringUtils.isNotBlank(endpoint.getJsonRootNode())) {
			rootNode = rootNode.get(endpoint.getJsonRootNode());
		}

		if (rootNode == null) {
			throw new ValidationException("invalid.external.response");
		}

		List<JsonNode> items = new ArrayList<>();

		if (rootNode.isArray()) {
			rootNode.forEach(items::add);
		} else if (rootNode.isObject()) {
			items.add(rootNode);
		} else {
			throw new ValidationException("invalid.external.response.type");
		}

		List<DynamicFieldMapping> mappings = mappingRepository.findByEndpoint(endpoint);
		List<UserData> results = new ArrayList<>();

		for (JsonNode item : items) {

			UserData user = new UserData();

			for (DynamicFieldMapping mapping : mappings) {

				JsonNode value = item.at(mapping.getExternalField());

				Object finalVal = convertByType(value, mapping.getDataType());

				setFieldValue(user, mapping.getInternalField(), finalVal);
			}
			user.setUuid(CommonUtility.generateUuid(userDataRepository));
			user.setActive(Boolean.TRUE);
			userDataRepository.save(user);
			results.add(user);
		}

		log.info("Outside ResponseParsingServiceImpl::parseAndConvert");

		return results;
	}

	private void setFieldValue(UserData user, String fieldName, Object value) throws ValidationException {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, UserData.class);
			Method setter = pd.getWriteMethod();
			setter.invoke(user, value);
		} catch (Exception e) {
			throw new ValidationException("Failed to set field: " + fieldName, e);
		}
	}

	private Object convertByType(JsonNode value, DataType type) {

		if (value == null || value.isMissingNode() || value.isNull()) {
			return null;
		}

		return switch (type) {
		case STRING -> value.asText();
		case NUMBER -> value.isNumber() ? value.numberValue() : null;
		case BOOLEAN -> value.asBoolean();
		case DATE -> LocalDate.parse(value.asText());
		};
	}

}
