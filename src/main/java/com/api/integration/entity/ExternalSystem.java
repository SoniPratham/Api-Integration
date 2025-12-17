package com.api.integration.entity;

import com.api.integration.enums.AuthType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: Kody Technolab Ltd. <br/>
 * @date    : Dec 14, 2025
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "external_system")
public class ExternalSystem extends CommonModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 2898816019088900090L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uuid;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String baseUrl;

	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@Column(nullable = false, length = 2000)
	private String authValue;
}
