package com.api.integration.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.api.integration.dto.DynamicFieldMappingResponseDTO;
import com.api.integration.dto.FieldMappingDTO;
import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.entity.EndpointConfiguration;

@Mapper(componentModel = "spring", uses = { DataTypeMapper.class })
public interface DynamicFieldMappingMapper {

	@Mapping(target = "uuid", ignore = true)
	@Mapping(target = "dataType", source = "dto.dataType")
	DynamicFieldMapping toEntity(FieldMappingDTO dto, EndpointConfiguration endpoint);

	@Mapping(target = "dataType", source = "dto.dataType")
	void updateEntity(@MappingTarget DynamicFieldMapping entity, FieldMappingDTO dto);

	@Mapping(target = "dataType", source = "dataType")
	@Mapping(target = "endpointUuid", source = "endpoint.uuid")
	DynamicFieldMappingResponseDTO toResponseDTO(DynamicFieldMapping mapping);

	List<DynamicFieldMappingResponseDTO> toResponseDTOs(List<DynamicFieldMapping> list);
}
