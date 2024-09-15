package org.vueverse.usermanagement.presentation;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;
    private String email;
}
