package com.api.integration.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.api.integration.dto.UserDataFilterDTO;
import com.api.integration.entity.UserData;
import com.api.integration.repo.UserDataRepository;
import com.api.integration.service.UserDataService;
import com.api.integration.specification.UserDataSpecification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataServiceImpl implements UserDataService {

	private final UserDataRepository userDataRepository;

	@Override
	public Page<UserData> list(Integer pageNumber, Integer pageSize, UserDataFilterDTO filter) {

		log.info("Inside UserDataServiceImpl::list -> pageNumber {}, pageSize {}, filter {}",
				pageNumber, pageSize, filter);

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));

		Specification<UserData> spec = UserDataSpecification.getListSpecification(filter);

		Page<UserData> page = userDataRepository.findAll(spec, pageable);

		log.info("Outside UserDataServiceImpl::list");

		return page;
	}
}
