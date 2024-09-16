package org.vueverse.usermanagement.presentation;

import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

@Data
public class LoginUserDto {
    private String identifier;

//    @NotBlank(message = "{general.not.blank}")
//    @Pattern(regexp = "^(?!.* )(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$", message = "{password.pattern}")
    private String password;

    private LoginType loginType;

    public enum LoginType {
        USERNAME, EMAIL, PHONE_NUMBER
    }
}
