package org.vueverse.usermanagement.infrastructure.security.mapper;

//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    @Mapping(target = "username", qualifiedByName = "getUsernameAndEmail")
//    UserEntity convertToEntity(LoginUserDto loginUserDto);
//
//    @Named("getUsernameAndEmail")
//    default String getUsernameAndEmail(String username, String email) {
//        if (username == null && email == null) {
//            throw new IllegalArgumentException("Either username or email must be provided");
//        }
//
//        return username != null ? username : email;
//    }
//}
