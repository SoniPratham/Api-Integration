package com.api.integration.service;

import java.util.List;

import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.UserData;

public interface ResponseParsingService {
	List<UserData> parseAndConvert(Object response, EndpointConfiguration endpoint) throws Exception;
}
