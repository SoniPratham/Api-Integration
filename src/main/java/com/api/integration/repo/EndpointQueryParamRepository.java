package com.api.integration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.integration.entity.EndpointConfiguration;
import com.api.integration.entity.EndpointQueryParam;
import com.api.integration.entity.ExternalSystem;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Repository
public interface EndpointQueryParamRepository extends CustomJpaRepository<EndpointQueryParam>, JpaSpecificationExecutor<ExternalSystem> {

	/**
	 * @param  endpoint
	 * @return
	 */
	List<EndpointQueryParam> findByEndpoint(EndpointConfiguration endpoint);

}