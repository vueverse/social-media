package org.vueverse.usermanagement.applicatoin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;
import org.vueverse.usermanagement.infrastructure.security.mapper.UserMapper;
import org.vueverse.usermanagement.infrastructure.security.repository.UserJpaRepository;
import org.vueverse.usermanagement.presentation.LoginResponse;
import org.vueverse.usermanagement.presentation.LoginUserDto;
import org.vueverse.usermanagement.presentation.RegisterUserDto;

import static org.vueverse.usermanagement.infrastructure.security.service.CustomUserDetailsService.getUserByUsernameOrEmailOrPhoneNumber;

@RequiredArgsConstructor
@Service
public class AuthenticationUser {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final GenerateJwt generateJwt;
    private final UserJpaRepository userRepository;

    public LoginResponse signup(RegisterUserDto input) {
        var user = createUserEntity(input);
        var userEntitySaved = userRepository.save(user);
        var userDetails = getUserDetails(userEntitySaved);
        String token = generateJwt.generateToken(userDetails);
        return new LoginResponse(token, generateJwt.getExpirationTime());
    }


    public LoginResponse authenticate(LoginUserDto loginUserDto) {
        UserEntity user = mapper.convertToEntity(loginUserDto);
//        user.getUsername() == null ? user.getEmail() : user.getUsername().v;
//        jwtService.generateToken(user.getUsername())
        return null;
    }

    private UserEntity createUserEntity(RegisterUserDto input) {
        return UserEntity.builder()
                .email(input.getEmail())
                .username(input.getUsername())
                .phoneNumber(input.getPhoneNumber())
                .password(passwordEncoder.encode(input.getPassword())).build();
    }

    private UserDetails getUserDetails(UserEntity userEntitySaved) {
        String identified = getUserByUsernameOrEmailOrPhoneNumber(userEntitySaved);
        return User.builder()
                .username(identified)
                .password(userEntitySaved.getPassword())
                .build();
    }
}
