package com.api.integration.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.api.integration.dto.UserDataFilterDTO;
import com.api.integration.entity.UserData;

import jakarta.persistence.criteria.Predicate;

public class UserDataSpecification {

	private UserDataSpecification() {
	}

	public static Specification<UserData> getListSpecification(UserDataFilterDTO filter) {

		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (filter.getSearchKeyword() != null) {
				String key = "%" + filter.getSearchKeyword().toLowerCase() + "%";
				Predicate p1 = cb.like(cb.lower(root.get("name")), key);
				Predicate p2 = cb.like(cb.lower(root.get("email")), key);
				predicates.add(cb.or(p1, p2));
			}

			if (filter.getStartDate() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.getStartDate()));
			}

			if (filter.getEndDate() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.getEndDate()));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
