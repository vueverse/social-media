package org.vueverse.usermanagement.applicatoin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;
import org.vueverse.usermanagement.infrastructure.security.mapper.UserMapper;
import org.vueverse.usermanagement.infrastructure.security.repository.UserJpaRepository;
import org.vueverse.usermanagement.presentation.LoginResponse;
import org.vueverse.usermanagement.presentation.LoginUserDto;
import org.vueverse.usermanagement.presentation.RegisterUserDto;

@RequiredArgsConstructor
@Service
public class AuthenticationUser {

    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final GenerateJwt generateJwt;
    private final UserJpaRepository userRepository;

    public LoginResponse signup(RegisterUserDto input) {
        var user = UserEntity.builder()
                .email(input.getEmail())
                .username(input.getUsername())
                .phoneNumber(input.getPhoneNumber())
                .password(passwordEncoder.encode(input.getPassword())).build();
        var userEntitySaved = userRepository.save(user);

        String token = generateJwt.generateToken(userEntitySaved);
        return new LoginResponse(token, generateJwt.getExpirationTime());
    }

    public LoginResponse authenticate(LoginUserDto loginUserDto) {
        UserEntity user = mapper.convertToEntity(loginUserDto);
//        user.getUsername() == null ? user.getEmail() : user.getUsername().v;
//        jwtService.generateToken(user.getUsername())
        return null;
    }
    //        String password = input.getPassword();
//        String username = input.getUsername();
//        var authentication = new UsernamePasswordAuthenticationToken(username, password);
//        authenticationManager.authenticate(authentication);
////        String jwtToken = jwtService.generateToken(authentication);
//        var loginResponse = new LoginResponse();
////        loginResponse.setToken(jwtToken);
//        loginResponse.setExpiresIn(jwtService.getExpirationTime());
//        return loginResponse;
////        return userRepository.findByEmail(input.getUsername()).orElseThrow();
}
