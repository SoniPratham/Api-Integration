package com.api.integration.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.api.integration.entity.UserData;

@Repository
public interface UserDataRepository extends CustomJpaRepository<UserData>, JpaSpecificationExecutor<UserData> {
}
