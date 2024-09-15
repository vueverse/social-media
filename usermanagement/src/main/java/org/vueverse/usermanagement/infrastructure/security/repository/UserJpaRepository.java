package org.vueverse.usermanagement.infrastructure.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
}
