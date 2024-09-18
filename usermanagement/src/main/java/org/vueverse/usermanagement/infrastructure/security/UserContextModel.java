package org.vueverse.usermanagement.infrastructure.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;

import java.util.Collection;

import static org.vueverse.usermanagement.infrastructure.security.service.CustomUserDetailsService.getUserByUsernameOrEmailOrPhoneNumber;

@Setter
@Getter
public class UserContextModel extends User {
    private Long userId;
    private String phoneNumber;
    private String email;


    public UserContextModel(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(getUserByUsernameOrEmailOrPhoneNumber(userEntity), userEntity.getPassword(), authorities);
        this.userId = userEntity.getId();
        this.phoneNumber = userEntity.getNumberOfPhoneNumber();
        this.email = userEntity.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
