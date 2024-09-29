//package org.vueverse.usermanagement.infrastructure.security.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.vueverse.usermanagement.infrastructure.security.entity.UserEntity;
//import org.vueverse.usermanagement.infrastructure.security.repository.UserJpaRepository;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final UserJpaRepository userJpaRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
//        UserEntity user = userJpaRepository.findByUsernameOrEmailOrPhoneNumber(identifier)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found" + identifier));
//        String primaryIdentifier = getUserByUsernameOrEmailOrPhoneNumber(user);
//        return new User(primaryIdentifier, user.getPassword(), new HashSet<>());
//    }
//
//    public static String getUserByUsernameOrEmailOrPhoneNumber(UserEntity user) {
//        return Optional.ofNullable(user.getEmail())
//                .or(() -> Optional.of(user.getNumberOfPhoneNumber() + user.getReginOfPhoneNumber()))
//                .or(() -> Optional.ofNullable(user.getUsername()))
//                .orElseThrow(() -> new IllegalArgumentException("user must have email or phone number or username  "));
//    }
//}
