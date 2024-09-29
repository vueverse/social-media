package org.vueverse.usermanagement.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.vueverse.usermanagement.applicatoin.AuthenticationUser;
import org.vueverse.usermanagement.presentation.dto.AuthResponse;
import org.vueverse.usermanagement.presentation.dto.LoginUserDto;
import org.vueverse.usermanagement.presentation.dto.RegisterUserDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/authentication")
public class AuthController {

    private final AuthenticationUser authenticationService;

//    @GetMapping("/")
//    public String oath(OAuth2AuthenticationToken token) {
//        return token.getPrincipal().toString();
//    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        var loginResponse = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            AuthResponse loginResponse = authenticationService.login(loginUserDto);
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> login() {
        authenticationService.logout();
        return ResponseEntity.ok().build();
    }
}