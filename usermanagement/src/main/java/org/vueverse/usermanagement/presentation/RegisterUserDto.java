package org.vueverse.usermanagement.presentation;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String username;
    private String email;
    private String phoneNumber;
    private String password;
}
