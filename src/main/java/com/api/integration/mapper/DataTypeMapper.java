package com.api.integration.mapper;

import org.mapstruct.Mapper;

import com.api.integration.enums.DataType;

@Mapper(componentModel = "spring")
public class DataTypeMapper {

	public DataType toEnum(String value) {
		return DataType.getByValue(value);
	}

	public String toString(DataType dataType) {
		return dataType != null ? dataType.getType() : null;
	}
}
