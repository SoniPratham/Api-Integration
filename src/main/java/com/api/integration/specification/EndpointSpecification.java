package com.api.integration.specification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.api.integration.dto.EndpointFilterDTO;
import com.api.integration.entity.EndpointConfiguration;

import jakarta.persistence.criteria.Predicate;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
public class EndpointSpecification {

	private EndpointSpecification() {
	}

	public static Specification<EndpointConfiguration> filter(EndpointFilterDTO filter) {
		return (root, query, builder) -> {
			List<Predicate> list = new ArrayList<>();

			if (StringUtils.isNotBlank(filter.getEndpointName())) {
				list.add(builder.like(builder.lower(root.get("endpointName")),
						"%" + filter.getEndpointName().toLowerCase() + "%"));
			}

			if (StringUtils.isNotBlank(filter.getExternalSystemUuid())) {
				list.add(builder.equal(root.get("externalSystem").get("uuid"),
						filter.getExternalSystemUuid()));
			}

			return builder.and(list.toArray(new Predicate[0]));
		};
	}
}
