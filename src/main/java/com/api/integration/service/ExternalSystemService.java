package com.api.integration.service;

import org.springframework.data.domain.Page;

import com.api.integration.dto.ExternalSystemFilterDTO;
import com.api.integration.dto.ExternalSystemRequestDTO;
import com.api.integration.dto.ExternalSystemResponseDTO;
import com.api.integration.entity.ExternalSystem;
import com.api.integration.exception.NotFoundException;
import com.api.integration.exception.ValidationException;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
public interface ExternalSystemService {

	/**
	 *
	 * @param  dto
	 * @return
	 * @throws ValidationException
	 */
	ExternalSystemResponseDTO addExternalSystem(ExternalSystemRequestDTO dto)
			throws ValidationException;

	/**
	 *
	 * @param  dto
	 * @throws ValidationException
	 * @throws NotFoundException
	 */
	void updateExternalSystem(ExternalSystemRequestDTO dto)
			throws ValidationException, NotFoundException;

	/**
	 *
	 * @param  uuid
	 * @return
	 * @throws NotFoundException
	 */
	ExternalSystemResponseDTO getExternalSystemDetails(String uuid)
			throws NotFoundException;

	/**
	 *
	 * @param  pageNumber
	 * @param  pageSize
	 * @param  filter
	 * @return
	 */
	Page<ExternalSystem> getList(Integer pageNumber, Integer pageSize, ExternalSystemFilterDTO filter);

	/**
	 *
	 * @param  uuid
	 * @param  active
	 * @throws ValidationException
	 * @throws NotFoundException
	 */
	void changeStatus(String uuid, Boolean active)
			throws ValidationException, NotFoundException;

	/**
	 * @param  uuid
	 * @return
	 * @throws NotFoundException
	 */
	ExternalSystem getExternalSystem(String uuid) throws NotFoundException;
}
