package com.api.integration.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
	private List<?> list;
	private Boolean hasNext;
	private Boolean hasPrevious;
	private Integer currentPageNumber;
	private Integer pageSize;
	private Integer totalCount;
}