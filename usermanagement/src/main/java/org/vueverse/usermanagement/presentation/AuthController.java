package org.vueverse.usermanagement.presentation;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Void> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signup(registerUserDto);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        LoginResponse loginResponse = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}