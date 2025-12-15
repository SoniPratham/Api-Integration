package com.api.integration.service;

import org.springframework.data.domain.Page;

import com.api.integration.dto.EndpointFilterDTO;
import com.api.integration.dto.EndpointRequestDTO;
import com.api.integration.dto.EndpointResponseDTO;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.exception.NotFoundException;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
public interface EndpointConfigurationService {

	/**
	 *
	 * @param  dto
	 * @return
	 * @throws NotFoundException
	 */
	EndpointResponseDTO add(EndpointRequestDTO dto) throws NotFoundException;

	/**
	 *
	 * @param  dto
	 * @return
	 * @throws NotFoundException
	 */
	EndpointResponseDTO update(EndpointRequestDTO dto) throws NotFoundException;

	/**
	 *
	 * @param  uuid
	 * @return
	 * @throws NotFoundException
	 */
	EndpointResponseDTO getDetails(String uuid) throws NotFoundException;

	/**
	 *
	 * @param  pageNumber
	 * @param  pageSize
	 * @param  filter
	 * @return
	 */
	Page<EndpointConfiguration> getList(Integer pageNumber, Integer pageSize, EndpointFilterDTO filter);

}
