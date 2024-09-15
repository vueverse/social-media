package org.vueverse.usermanagement.infrastructure.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;
import org.vueverse.usermanagement.presentation.LoginUserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "username", qualifiedByName = "getUsernameAndEmail")
    UserEntity convertToEntity(LoginUserDto loginUserDto);

    @Named("getUsernameAndEmail")
    default String getUsernameAndEmail(String username, String email) {
        if (username == null && email == null) {
            throw new IllegalArgumentException("Either username or email must be provided");
        }

        return username != null ? username : email;
    }
}
