package org.vueverse.usermanagement.presentation;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.vueverse.usermanagement.infrastructure.security.entity.PhoneNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterUserDto {

    private String username;
    private String email;
    private PhoneNumberDto phoneNumber;
    @NotBlank(message = "{general.not.blank}")
    @Pattern(regexp = "^(?!.* )(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,100}$", message = "password is not valid")
    private String password;

    @Getter
    record PhoneNumberDto(@NotNull @NotBlank String number, @NotNull @NotBlank String regin) {

    }

    public String getNumber() {
        return phoneNumber.number();
    }

    public String getRegin() {
        return phoneNumber.regin();
    }
}
