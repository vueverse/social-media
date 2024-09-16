package org.vueverse.usermanagement.applicatoin;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.vueverse.usermanagement.infrastructure.security.entity.PhoneNumber;

public record PhoneNumberValidation(PhoneNumber phoneNumber) {

    private void validatePhoneNumber(String phoneNumber) {
        PhoneNumberUtil validation = PhoneNumberUtil.getInstance();
        String defaultRegion = Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name();
        Phonenumber.PhoneNumber number = validation.parse(phoneNumber, defaultRegion);

        boolean possibleNumber = validation.isPossibleNumber(phoneNumber, "");
        boolean validNumber = validation.isValidNumber(number);
        if (!(possibleNumber && validNumber))
            throw new IllegalArgumentException("phoneNumber  is not valid");
    }
}
