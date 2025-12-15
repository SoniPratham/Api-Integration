package com.api.integration.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.integration.entity.EndpointPathParam;
import com.api.integration.entity.ExternalSystem;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Repository
public interface EndpointPathParamRepository extends CustomJpaRepository<EndpointPathParam>, JpaSpecificationExecutor<ExternalSystem> {

}