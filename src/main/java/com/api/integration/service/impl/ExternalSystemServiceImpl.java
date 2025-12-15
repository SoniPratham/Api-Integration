package com.api.integration.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.integration.dto.ExternalSystemFilterDTO;
import com.api.integration.dto.ExternalSystemRequestDTO;
import com.api.integration.dto.ExternalSystemResponseDTO;
import com.api.integration.entity.ExternalSystem;
import com.api.integration.enums.AuthType;
import com.api.integration.exception.NotFoundException;
import com.api.integration.exception.ValidationException;
import com.api.integration.locale.MessageByLocaleService;
import com.api.integration.mapper.ExternalSystemMapper;
import com.api.integration.repo.ExternalSystemRepository;
import com.api.integration.service.ExternalSystemService;
import com.api.integration.specification.ExternalSystemSpecification;
import com.api.integration.utils.CommonUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor
@Slf4j
public class ExternalSystemServiceImpl implements ExternalSystemService {

	private final ExternalSystemRepository externalSystemRepository;
	private final MessageByLocaleService messageByLocaleService;
	private final ExternalSystemMapper externalSystemMapper;

	@Override
	public ExternalSystemResponseDTO addExternalSystem(ExternalSystemRequestDTO dto)
			throws ValidationException {

		log.info("Inside ExternalSystemServiceImpl::addExternalSystem -> dto: {}", dto);

		if (externalSystemRepository.existsByName(dto.getName())) {
			throw new ValidationException(messageByLocaleService.getMessage(
					"external.system.name.exists", null));
		}

		ExternalSystem entity = externalSystemMapper.toEntity(dto);
		entity.setUuid(CommonUtility.generateUuid(externalSystemRepository));
		entity.setActive(Boolean.TRUE);
		ExternalSystem saved = externalSystemRepository.save(entity);

		log.info("Outside ExternalSystemServiceImpl::addExternalSystem");

		return externalSystemMapper.toResponseDto(saved);
	}

	@Override
	public void updateExternalSystem(ExternalSystemRequestDTO dto)
			throws ValidationException, NotFoundException {

		log.info("Inside ExternalSystemServiceImpl::updateExternalSystem -> dto: {}", dto);

		ExternalSystem existing = externalSystemRepository
				.findByUuidAndActive(dto.getUuid(), Boolean.TRUE)
				.orElseThrow(() -> new NotFoundException(messageByLocaleService.getMessage(
						"external.system.not.found.uuid", new Object[] { dto.getUuid() })));

		if (externalSystemRepository.existsByNameAndUuidNot(dto.getName(), dto.getUuid())) {
			throw new ValidationException(messageByLocaleService.getMessage(
					"external.system.name.exists", null));
		}

		BeanUtils.copyProperties(dto, existing, "id", "uuid", "isArchive");
		existing.setAuthType(AuthType.getByValue(dto.getAuthType()));
		externalSystemRepository.save(existing);

		log.info("Outside ExternalSystemServiceImpl::updateExternalSystem");
	}

	@Override
	public ExternalSystemResponseDTO getExternalSystemDetails(String uuid)
			throws NotFoundException {

		log.info("Inside ExternalSystemServiceImpl::getExternalSystemDetails -> uuid {}", uuid);

		ExternalSystem entity = getExternalSystem(uuid);

		log.info("Outside ExternalSystemServiceImpl::getExternalSystemDetails");

		return externalSystemMapper.toResponseDto(entity);
	}

	/**
	 * @param  uuid
	 * @return
	 * @throws NotFoundException
	 */
	@Override
	public ExternalSystem getExternalSystem(String uuid) throws NotFoundException {
		return externalSystemRepository
				.findByUuid(uuid)
				.orElseThrow(() -> new NotFoundException(messageByLocaleService.getMessage(
						"external.system.not.found.uuid", new Object[] { uuid })));
	}

	@Override
	public Page<ExternalSystem> getList(Integer pageNumber, Integer pageSize, ExternalSystemFilterDTO filter) {

		log.info("Inside ExternalSystemServiceImpl::getList -> filter: {}", filter);

		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		return externalSystemRepository.findAll(ExternalSystemSpecification.getListSpec(filter), pageable);
	}

	@Override
	public void changeStatus(String uuid, Boolean active)
			throws ValidationException, NotFoundException {

		log.info("Inside ExternalSystemServiceImpl::changeStatus -> uuid {}, active {}", uuid, active);

		ExternalSystem entity = getExternalSystem(uuid);

		if (active == null) {
			throw new ValidationException(messageByLocaleService.getMessage("active.not.null", null));
		}

		if (entity.getActive().equals(active)) {
			throw new ValidationException(
					messageByLocaleService.getMessage(
							active ? "already.active" : "already.deactive", null));
		}

		entity.setActive(active);
		externalSystemRepository.save(entity);

		log.info("Outside ExternalSystemServiceImpl::changeStatus");
	}
}
