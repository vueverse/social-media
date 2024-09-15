package org.vueverse.usermanagement.applicatoin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final UserJpaRepository userRepository;
    private final UserMapper mapper;
    private final GenerateJwt jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public void signup(RegisterUserDto input) {
        var user = UserEntity.builder()
                .email(input.getEmail())
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword())).build();

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
