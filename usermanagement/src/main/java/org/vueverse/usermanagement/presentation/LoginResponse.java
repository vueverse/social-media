package org.vueverse.usermanagement.presentation;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}
