package com.api.integration.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.integration.dto.ApiExecutionRequestDTO;
import com.api.integration.dto.GenericApiResponseDTO;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.EndpointQueryParam;
import com.api.integration.entity.ExternalSystem;
import com.api.integration.entity.UserData;
import com.api.integration.exception.NotFoundException;
import com.api.integration.repo.EndpointConfigurationRepository;
import com.api.integration.repo.EndpointPathParamRepository;
import com.api.integration.repo.EndpointQueryParamRepository;
import com.api.integration.repo.ExternalSystemRepository;
import com.api.integration.service.GenericApiCallerService;
import com.api.integration.service.ResponseParsingService;
import com.api.integration.utils.JsonUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenericApiCallerServiceImpl implements GenericApiCallerService {

	private final ExternalSystemRepository externalSystemRepository;
	private final EndpointConfigurationRepository endpointRepository;
	private final EndpointQueryParamRepository queryParamRepository;
	private final EndpointPathParamRepository pathParamRepository;
	private final RestTemplate restTemplate;
	private final ResponseParsingService responseParsingService;

	@Override
	public GenericApiResponseDTO execute(ApiExecutionRequestDTO request) throws Exception {

		log.info("Inside GenericApiCallerServiceImpl::execute -> request {}", request);

		ExternalSystem system = externalSystemRepository.findByUuid(request.getExternalSystemUuid())
				.orElseThrow(() -> new NotFoundException("external.system.not.found"));

		EndpointConfiguration endpoint = endpointRepository.findByUuid(request.getEndpointUuid())
				.orElseThrow(() -> new NotFoundException("endpoint.not.found"));

		String finalUrl = buildFinalUrl(system, endpoint, request);

		HttpHeaders headers = buildHeaders(system);
		HttpEntity<String> entity = buildRequestBody(endpoint, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				finalUrl,
				HttpMethod.valueOf(endpoint.getHttpMethod().toString()),
				entity,
				String.class);

		log.info("API Response: {}", response.getBody());

		// Parse and convert to TempUser list
		List<UserData> users = responseParsingService.parseAndConvert(JsonUtils.toObject(response.getBody()), endpoint);

		GenericApiResponseDTO resp = new GenericApiResponseDTO();
		resp.setSuccess(true);
		resp.setRawResponse(users);

		log.info("Outside GenericApiCallerServiceImpl::execute");

		return resp;
	}

	private HttpHeaders buildHeaders(ExternalSystem system) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		switch (system.getAuthType()) {
		case BEARER -> headers.set("Authorization", "Bearer " + system.getAuthValue());
		case API_KEY -> headers.set("X-API-Key", system.getAuthValue());
		case OAUTH -> headers.set("Authorization", "OAuth " + system.getAuthValue());
		}

		return headers;
	}

	private String buildFinalUrl(ExternalSystem system, EndpointConfiguration endpoint, ApiExecutionRequestDTO req) {

		String url = system.getBaseUrl() + endpoint.getPath();

		// Replace path params
		if (!CollectionUtils.isEmpty(req.getPathParams())) {
			for (Map.Entry<String, String> entry : req.getPathParams().entrySet()) {
				url = url.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		}

		// Append query params
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		List<EndpointQueryParam> savedParams = queryParamRepository.findByEndpoint(endpoint);

		for (EndpointQueryParam qp : savedParams) {
			builder.queryParam(qp.getKey(), qp.getValueTemplate());
		}

		if (!CollectionUtils.isEmpty(req.getQueryParams())) {
			req.getQueryParams().forEach(builder::queryParam);
		}

		return builder.toUriString();
	}

	private HttpEntity<String> buildRequestBody(EndpointConfiguration endpoint, HttpHeaders headers) {
		if (endpoint.getRequestBodyTemplate() == null) {
			return new HttpEntity<>(headers);
		}
		return new HttpEntity<>(endpoint.getRequestBodyTemplate(), headers);
	}
}
