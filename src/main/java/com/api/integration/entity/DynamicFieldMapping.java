package com.api.integration.entity;

import com.api.integration.enums.DataType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "dynamic_field_mapping")
@Data
@EqualsAndHashCode(callSuper = true)
public class DynamicFieldMapping extends CommonModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uuid;

	/**
	 *
	 */
	private static final long serialVersionUID = -6469454615153638050L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endpoint_id", nullable = false)
	private EndpointConfiguration endpoint;

	@Column(name = "external_field", nullable = false)
	private String externalField; // field coming from external API

	@Column(name = "internal_field", nullable = false)
	private String internalField; // field in our system

	@Enumerated(EnumType.STRING)
	@Column(name = "data_type", nullable = false)
	private DataType dataType;
}
