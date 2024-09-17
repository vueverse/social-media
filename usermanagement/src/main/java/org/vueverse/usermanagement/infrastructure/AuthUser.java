package org.vueverse.usermanagement.infrastructure;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vueverse.usermanagement.infrastructure.security.UserContextModel;

public class AuthUser {
    public static UserContextModel getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof UserContextModel))
            throw new IllegalArgumentException("user is not a authenticated ");

        return (UserContextModel) authentication.getPrincipal();

    }
}
