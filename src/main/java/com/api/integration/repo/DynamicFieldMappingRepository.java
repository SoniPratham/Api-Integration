package com.api.integration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.integration.entity.DynamicFieldMapping;
import com.api.integration.entity.EndpointConfiguration;

@Repository
public interface DynamicFieldMappingRepository
		extends CustomJpaRepository<DynamicFieldMapping>, JpaSpecificationExecutor<DynamicFieldMapping> {

	/**
	 * @param  endpointUuid
	 * @return
	 */
	List<DynamicFieldMapping> findByEndpointUuid(String endpointUuid);

	/**
	 * @param  endpoint
	 * @return
	 */
	List<DynamicFieldMapping> findByEndpoint(EndpointConfiguration endpoint);

}
