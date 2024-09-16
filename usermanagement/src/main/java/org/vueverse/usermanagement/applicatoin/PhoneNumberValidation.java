package org.vueverse.usermanagement.applicatoin;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.vueverse.usermanagement.infrastructure.security.entity.PhoneNumber;

@Slf4j
public record PhoneNumberValidation(PhoneNumber phoneNumber) {
    public PhoneNumberValidation {
        validatePhoneNumber(phoneNumber.getNumber(), phoneNumber.getRegin());
    }

    private void validatePhoneNumber(String number, String regen) {
        PhoneNumberUtil validation = PhoneNumberUtil.getInstance();
        String defaultRegion = Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name();
        Phonenumber.PhoneNumber phone_number = null;
        phone_number = getPhoneNumber(phone_number, validation, defaultRegion);
        boolean possibleNumber = validation.isPossibleNumber(number, regen);
        boolean validNumber = validation.isValidNumber(phone_number);
        if (!(possibleNumber && validNumber))
            throw new IllegalArgumentException("phoneNumber  is not valid");
    }

    private Phonenumber.PhoneNumber getPhoneNumber(Phonenumber.PhoneNumber number, PhoneNumberUtil validation,
                                                   String defaultRegion) {
        try {
            number = validation.parse(phoneNumber.getNumber(), defaultRegion);
        } catch (NumberParseException e) {
            log.error(" Invalid phone number {}", e.getMessage());
        }
        return number;
    }
}
