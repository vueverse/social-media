package org.vueverse.usermanagement.infrastructure.security.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity  u  WHERE   u.username=:identifier OR u.email = :identifier OR u.password = :identifier")
    Optional<UserEntity> findByUsernameOrEmailOrPhoneNumber(String identifier);
}
