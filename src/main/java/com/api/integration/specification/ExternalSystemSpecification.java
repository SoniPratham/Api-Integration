package com.api.integration.specification;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.api.integration.dto.ExternalSystemFilterDTO;
import com.api.integration.entity.ExternalSystem;

import jakarta.persistence.criteria.Predicate;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
public class ExternalSystemSpecification {

	private ExternalSystemSpecification() {
	}

	public static Specification<ExternalSystem> getListSpec(ExternalSystemFilterDTO filter) {
		return (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<>();

			if (StringUtils.isNotBlank(filter.getSearchKeyword())) {
				Predicate predicate = cb.like(cb.lower(root.get("name")),
						"%" + filter.getSearchKeyword().trim().toLowerCase() + "%");
				predicates.add(cb.or(predicate));
			}

			if (filter.getActive() != null) {
				predicates.add(cb.equal(root.get("active"), filter.getActive()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
