package com.api.integration.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * Author: Kody Technolab Ltd. <br/>
 * Date : 13-May-2024
 */
@NoRepositoryBean
public interface CustomJpaRepository<T> extends JpaRepository<T, Long> {

	/**
	 *
	 * @param  uuid
	 * @return
	 */
	Optional<T> findByUuid(String uuid);

	/**
	 * @param  uuid
	 * @param  active
	 * @return
	 */
	Optional<T> findByUuidAndActive(String uuid, Boolean active);

	/**
	 * @param  uuid
	 * @return
	 */
	boolean existsByUuid(String uuid);

}
