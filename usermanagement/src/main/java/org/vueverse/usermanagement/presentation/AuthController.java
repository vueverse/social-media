package org.vueverse.usermanagement.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vueverse.usermanagement.applicatoin.AuthenticationUser;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationUser authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        var loginResponse = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse loginResponse = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}