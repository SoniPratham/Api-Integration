package com.api.integration.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class TransectionSpecification<Transection> {

	public Specification<Transection> filterByAmount(Double transectionAmount)
	{
		return new Specification<Transection>() {

			private static final long serialVersionUID = 3997160279256464030L;

			@Override
			public Predicate toPredicate(Root<Transection> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				return criteriaBuilder.equal(root.get("transectionAmount"), transectionAmount);
			}

			
		};
	}
	public Specification<Transection> filterByTransectionCategoryName(String transectionCategoryName)
	{
		return new Specification<Transection>() {

			private static final long serialVersionUID = 3997160279256464030L;

			@Override
			public Predicate toPredicate(Root<Transection> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				return criteriaBuilder.equal(root.join("transectionCategory").get("categoryName"), transectionCategoryName);
			}

			
		};
	}
	public Specification<Transection> filterByTransectionType(String transectionType)
	{
		return new Specification<Transection>() {

			private static final long serialVersionUID = 3997160279256464030L;

			@Override
			public Predicate toPredicate(Root<Transection> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				return criteriaBuilder.equal(root.join("transectionType").get("transectionTypeName"), transectionType);
			}

			
		};
	}
	public Specification<Transection> filterByTransectionTitle(String transectionTitle)
	{
		return new Specification<Transection>() {

			private static final long serialVersionUID = 3997160279256464030L;

			@Override
			public Predicate toPredicate(Root<Transection> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				return criteriaBuilder.equal(root.get("transectionTitle"), transectionTitle);
			}

			
		};
	}
}
