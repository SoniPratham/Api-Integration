package com.api.integration.service;

import org.springframework.data.domain.Page;

import com.api.integration.dto.UserDataFilterDTO;
import com.api.integration.entity.UserData;

public interface UserDataService {

	/**
	 *
	 * @param  pageNumber
	 * @param  pageSize
	 * @param  filter
	 * @return
	 */
	Page<UserData> list(Integer pageNumber, Integer pageSize, UserDataFilterDTO filter);
}
