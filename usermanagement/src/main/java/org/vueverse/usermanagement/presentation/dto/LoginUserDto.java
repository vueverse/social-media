package org.vueverse.usermanagement.presentation.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class LoginUserDto {

    @NotNull
    @NotBlank
    private String identifier;

    @NotBlank(message = "{general.not.blank}")
    @Pattern(regexp = "^(?!.* )(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$", message = "password is not valid")
    private String password;

    @NotNull
    private LoginType loginType;

    public enum LoginType {
        USERNAME, EMAIL, PHONE_NUMBER
    }
}
