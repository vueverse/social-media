package org.vueverse.usermanagement.presentation;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterUserDto {
    private String username;
    private String email;
    private PhoneNumberDto phoneNumber;
    private String password;

    @Getter
    record PhoneNumberDto(String number, String regin) {
    }

    public String getNumber() {
        return phoneNumber.number();
    }

    public String getRegin() {
        return phoneNumber.regin();
    }
}
