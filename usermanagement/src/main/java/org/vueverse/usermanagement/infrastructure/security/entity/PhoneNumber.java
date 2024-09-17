package org.vueverse.usermanagement.infrastructure.security.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.vueverse.usermanagement.applicatoin.PhoneNumberValidation;
import org.vueverse.usermanagement.presentation.dto.LoginUserDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PhoneNumber {
    private String number;
    private String regin;

    public static PhoneNumber createPhoneNumber(LoginUserDto loginUserDto) {
        final int PART_ONE_NUMBER_OF_PHONE_NUMBER = 1;
        final int PART_ZERO_REGEN_OF_PHONE_NUMBER = 0;
        String[] split = loginUserDto.getIdentifier().split(",");
        String number = split[PART_ONE_NUMBER_OF_PHONE_NUMBER];
        String regin = split[PART_ZERO_REGEN_OF_PHONE_NUMBER];
        var phoneNumber = new PhoneNumber(number, regin);
        new PhoneNumberValidation(phoneNumber);
        return phoneNumber;
    }
}
