
package com.api.integration.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

/**
 *
 * Author: Kody Technolab Ltd. <br/>
 * Date : 09-May-2024
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class CommonModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7869831537646377462L;
	@Column(name = "active", nullable = false)
	private Boolean active;

	@CreationTimestamp
	@CreatedDate
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false, nullable = false)
	private Long createdBy;

	@LastModifiedBy
	@Column(name = "updated_by", nullable = false)
	private Long updatedBy;

}
