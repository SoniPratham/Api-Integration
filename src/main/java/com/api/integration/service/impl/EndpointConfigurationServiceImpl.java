package com.api.integration.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.integration.dto.EndpointFilterDTO;
import com.api.integration.dto.EndpointParamDTO;
import com.api.integration.dto.EndpointRequestDTO;
import com.api.integration.dto.EndpointResponseDTO;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.EndpointPathParam;
import com.api.integration.entity.EndpointQueryParam;
import com.api.integration.exception.NotFoundException;
import com.api.integration.mapper.EndpointConfigurationMapper;
import com.api.integration.repo.EndpointConfigurationRepository;
import com.api.integration.repo.EndpointPathParamRepository;
import com.api.integration.repo.EndpointQueryParamRepository;
import com.api.integration.service.EndpointConfigurationService;
import com.api.integration.service.ExternalSystemService;
import com.api.integration.specification.EndpointSpecification;
import com.api.integration.utils.CommonUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor
@Slf4j
public class EndpointConfigurationServiceImpl implements EndpointConfigurationService {

	private final EndpointConfigurationRepository endpointConfigurationRepository;
	private final EndpointQueryParamRepository queryParamRepository;
	private final EndpointPathParamRepository pathParamRepository;
	private final EndpointConfigurationMapper endpointConfigurationMapper;
	private final ExternalSystemService externalSystemService;

	@Override
	public EndpointResponseDTO add(EndpointRequestDTO dto) throws NotFoundException {

		log.info("Inside EndpointConfigurationServiceImpl::add -> dto {}", dto);

		EndpointConfiguration entity = endpointConfigurationMapper.toEntity(dto);
		entity.setUuid(CommonUtility.generateUuid(endpointConfigurationRepository));
		entity.setActive(true);
		entity.setExternalSystem(externalSystemService.getExternalSystem(dto.getExternalSystemUuid()));

		EndpointConfiguration saved = endpointConfigurationRepository.save(entity);

		saveQueryParams(dto.getQueryParams(), saved);
		savePathParams(dto.getPathParams(), saved);

		log.info("Outside EndpointConfigurationServiceImpl::add");

		return endpointConfigurationMapper.toResponseDTO(saved);
	}

	@Override
	public EndpointResponseDTO update(EndpointRequestDTO dto) throws NotFoundException {

		log.info("Inside EndpointConfigurationServiceImpl::update -> dto {}", dto);

		EndpointConfiguration existing = endpointConfigurationRepository.findByUuid(dto.getUuid())
				.orElseThrow(() -> new NotFoundException("endpoint.not.found"));

		EndpointConfiguration updated = endpointConfigurationMapper.toEntity(dto);
		updated.setId(existing.getId());
		updated.setUuid(existing.getUuid());
		updated.setActive(existing.getActive());
		updated.setExternalSystem(existing.getExternalSystem());

		EndpointConfiguration saved = endpointConfigurationRepository.save(updated);

		queryParamRepository.deleteAll(queryParamRepository.findAll());
		pathParamRepository.deleteAll(pathParamRepository.findAll());

		saveQueryParams(dto.getQueryParams(), saved);
		savePathParams(dto.getPathParams(), saved);

		log.info("Outside EndpointConfigurationServiceImpl::update");

		return endpointConfigurationMapper.toResponseDTO(saved);
	}

	private void saveQueryParams(List<EndpointParamDTO> paramDTOs, EndpointConfiguration endpoint) {
		if (paramDTOs != null) {
			paramDTOs.forEach(p -> {
				EndpointQueryParam param = new EndpointQueryParam();
				param.setKey(p.getKey());
				param.setUuid(CommonUtility.generateUuid(queryParamRepository));
				param.setActive(Boolean.TRUE);
				param.setValueTemplate(p.getValueTemplate());
				param.setEndpointConfiguration(endpoint);
				queryParamRepository.save(param);
			});
		}
	}

	private void savePathParams(List<EndpointParamDTO> paramDTOs, EndpointConfiguration endpoint) {
		if (paramDTOs != null) {
			paramDTOs.forEach(p -> {
				EndpointPathParam param = new EndpointPathParam();
				param.setUuid(CommonUtility.generateUuid(pathParamRepository));
				param.setActive(Boolean.TRUE);
				param.setKey(p.getKey());
				param.setValueTemplate(p.getValueTemplate());
				param.setEndpointConfiguration(endpoint);
				pathParamRepository.save(param);
			});
		}
	}

	@Override
	public EndpointResponseDTO getDetails(String uuid) throws NotFoundException {
		log.info("Inside EndpointConfigurationServiceImpl::getDetails -> uuid {}", uuid);

		EndpointConfiguration entity = endpointConfigurationRepository.findByUuid(uuid)
				.orElseThrow(() -> new NotFoundException("endpoint.not.found"));

		log.info("Outside EndpointConfigurationServiceImpl::getDetails");

		return endpointConfigurationMapper.toResponseDTO(entity);
	}

	@Override
	public Page<EndpointConfiguration> getList(Integer pageNumber, Integer pageSize, EndpointFilterDTO filter) {

		log.info("Inside EndpointConfigurationServiceImpl::getList -> filter {}", filter);

		PageRequest pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

		return endpointConfigurationRepository.findAll(EndpointSpecification.filter(filter), pageable);
	}
}
