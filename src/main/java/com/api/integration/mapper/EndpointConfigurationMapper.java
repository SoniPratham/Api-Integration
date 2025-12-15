package com.api.integration.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.api.integration.dto.EndpointRequestDTO;
import com.api.integration.dto.EndpointResponseDTO;
import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.enums.HttpMethodType;

import lombok.RequiredArgsConstructor;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Component
@RequiredArgsConstructor
public class EndpointConfigurationMapper {

	public EndpointConfiguration toEntity(EndpointRequestDTO dto) {
		EndpointConfiguration entity = new EndpointConfiguration();
		BeanUtils.copyProperties(dto, entity);
		entity.setHttpMethod(HttpMethodType.getByValue(dto.getHttpMethod()));
		return entity;
	}

	public EndpointResponseDTO toResponseDTO(EndpointConfiguration entity) {
		EndpointResponseDTO dto = new EndpointResponseDTO();
		BeanUtils.copyProperties(entity, dto);
		dto.setHttpMethod(entity.getHttpMethod().getMethod());
		dto.setExternalSystemUuid(entity.getExternalSystem().getUuid());
		return dto;
	}

	public List<EndpointResponseDTO> toResponseDTOs(List<EndpointConfiguration> list) {
		return list.stream().map(this::toResponseDTO).toList();
	}
}
