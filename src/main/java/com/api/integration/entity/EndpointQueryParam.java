package com.api.integration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Entity
@Table(name = "endpoint_query_param")
@Data
@EqualsAndHashCode(callSuper = false)
public class EndpointQueryParam extends CommonModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8860908525967357650L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", nullable = false)
	private String uuid;

	private String key;

	private String valueTemplate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endpoint_config_id")
	private EndpointConfiguration endpoint;
}
