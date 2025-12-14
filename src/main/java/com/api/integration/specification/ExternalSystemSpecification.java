package com.api.integration.specification;

import java.util.ArrayList;
import java.util.List;

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

			if (filter.getSearchKeyword() != null) {
				Predicate p1 = cb.like(cb.lower(root.get("name")),
						"%" + filter.getSearchKeyword().toLowerCase() + "%");
				predicates.add(cb.or(p1));
			}

			if (filter.getActive() != null) {
				predicates.add(cb.equal(root.get("active"), filter.getActive()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
