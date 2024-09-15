package org.vueverse.usermanagement.infrastructure.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;

import java.util.Optional;

public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
