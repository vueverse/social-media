package org.vueverse.usermanagement.applicatoin;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vueverse.usermanagement.infrastructure.security.UserContextModel;
import org.vueverse.usermanagement.infrastructure.security.entity.PhoneNumber;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;
import org.vueverse.usermanagement.infrastructure.security.repository.UserJpaRepository;
import org.vueverse.usermanagement.presentation.dto.AuthResponse;
import org.vueverse.usermanagement.presentation.dto.LoginUserDto;
import org.vueverse.usermanagement.presentation.dto.RegisterUserDto;

import java.util.HashSet;
import java.util.Objects;

import static org.vueverse.usermanagement.infrastructure.security.entity.PhoneNumber.createPhoneNumber;
import static org.vueverse.usermanagement.infrastructure.security.service.CustomUserDetailsService.getUserByUsernameOrEmailOrPhoneNumber;

@RequiredArgsConstructor
@Service
public class AuthenticationUser {

    private static final int MIN_LENGTH_OF_USER_NAME = 3;
    private final PasswordEncoder passwordEncoder;
    private final GenerateJwt generateJwt;
    private final UserJpaRepository userRepository;

    public AuthResponse signup(RegisterUserDto input) {
        var user = createUserEntity(input);
        var userEntitySaved = userRepository.save(user);
        var userDetails = getUserDetails(userEntitySaved);
        String token = generateJwt.generateToken(userDetails);
        return new AuthResponse(token, generateJwt.getExpirationTime());
    }


    public AuthResponse login(LoginUserDto loginUserDto) {
        UserEntity userEntity = identifierValidation(loginUserDto);

        if (!isMatchesPassword(loginUserDto, userEntity))
            throw new IllegalArgumentException("password is not valid");

        UserEntity user = userRepository.findByUsernameOrEmailOrPhoneNumber(loginUserDto.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        UserDetails userDetails = getUserDetails(user);

        String token = generateJwt.generateToken(userDetails);

        return new AuthResponse(token, generateJwt.getExpirationTime());
    }

    private boolean isMatchesPassword(LoginUserDto loginUserDto, UserEntity userEntity) {
        return passwordEncoder.matches(loginUserDto.getPassword(), userEntity.getPassword());
    }

    private UserEntity identifierValidation(LoginUserDto loginUserDto) {
        return validateLoginType(loginUserDto);

    }

    private UserEntity validateLoginType(LoginUserDto loginUserDto) {
        final String identifier = loginUserDto.getIdentifier();
        final String password = loginUserDto.getPassword();
        return switch (loginUserDto.getLoginType()) {
            case USERNAME -> {
                validateUsername(identifier);
                yield UserEntity.builder().username(identifier).password(password).build();
            }
            case EMAIL -> {
                validateEmail(identifier);
                yield UserEntity.builder().email(identifier).password(password).build();
            }
            case PHONE_NUMBER -> {
                var phoneNumber = createPhoneNumber(loginUserDto);
                yield UserEntity.builder().phoneNumber(phoneNumber).password(password).build();
            }
        };
    }

    private void validateEmail(String email) {
        var instance = EmailValidator.getInstance();
        boolean isValidEmailAddress = instance.isValid(email);
        if (!isValidEmailAddress)
            throw new IllegalArgumentException("email is not valid");
    }

    private void validateUsername(String username) {
        if (Objects.isNull(username) || username.isBlank() || username.length() < MIN_LENGTH_OF_USER_NAME)
            throw new IllegalArgumentException("Username is not a valid ");
    }

    private UserEntity createUserEntity(RegisterUserDto registerUserDto) {
        var phoneNumber = new PhoneNumber(registerUserDto.getNumber(), registerUserDto.getRegin());
        return UserEntity.builder()
                .email(registerUserDto.getEmail())
                .username(registerUserDto.getUsername())
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(registerUserDto.getPassword())).build();
    }

    private User getUserDetails(UserEntity userEntitySaved) {
        return new UserContextModel(userEntitySaved, new HashSet<>());
    }
}
