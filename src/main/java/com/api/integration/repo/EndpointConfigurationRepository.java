package com.api.integration.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.integration.entity.EndpointConfiguration;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Repository
public interface EndpointConfigurationRepository
		extends CustomJpaRepository<EndpointConfiguration>, JpaSpecificationExecutor<EndpointConfiguration> {

}