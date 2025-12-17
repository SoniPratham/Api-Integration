package com.api.integration.entity;

import com.api.integration.enums.HttpMethodType;

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

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Entity
@Table(name = "endpoint_configuration")
@Data
@EqualsAndHashCode(callSuper = false)
public class EndpointConfiguration extends CommonModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 911801058335706571L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uuid;

	private String endpointName;

	private String path;

	@Enumerated(EnumType.STRING)
	private HttpMethodType httpMethod;

	private String jsonRootNode;

	@Column(columnDefinition = "TEXT")
	private String requestBodyTemplate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "external_system_id")
	private ExternalSystem externalSystem;

}
