package org.vueverse.usermanagement.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vueverse.usermanagement.applicatoin.AuthenticationUser;
import org.vueverse.usermanagement.presentation.dto.AuthResponse;
import org.vueverse.usermanagement.presentation.dto.LoginUserDto;
import org.vueverse.usermanagement.presentation.dto.RegisterUserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/authentication")
public class AuthController {

    private final AuthenticationUser authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        var loginResponse = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserDto loginUserDto) {
        AuthResponse loginResponse = authenticationService.login(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}