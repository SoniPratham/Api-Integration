package com.api.integration.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class UserData extends CommonModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -2950483545995569646L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String uuid;

	private String name;

	private String email;

	private String phone;

	private String calendlyUserId;

	private String rawJson; // store raw calendly data for reference
}