package com.api.integration.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.api.integration.dto.ExternalSystemRequestDTO;
import com.api.integration.dto.ExternalSystemResponseDTO;
import com.api.integration.entity.ExternalSystem;
import com.api.integration.enums.AuthType;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Component
public class ExternalSystemMapper {

	public ExternalSystem toEntity(final ExternalSystemRequestDTO dto) {
		ExternalSystem entity = new ExternalSystem();
		BeanUtils.copyProperties(dto, entity);
		if (dto.getAuthType() != null) {
			entity.setAuthType(AuthType.getByValue(dto.getAuthType()));
		}
		return entity;
	}

	public ExternalSystemResponseDTO toResponseDto(final ExternalSystem entity) {
		ExternalSystemResponseDTO dto = new ExternalSystemResponseDTO();
		BeanUtils.copyProperties(entity, dto);
		if (entity.getAuthType() != null) {
			dto.setAuthType(entity.getAuthType().getAuthType());
		}
		return dto;
	}

	public List<ExternalSystemResponseDTO> toResponseDTOs(List<ExternalSystem> list) {
		return list.stream().map(this::toResponseDto).toList();
	}
}
