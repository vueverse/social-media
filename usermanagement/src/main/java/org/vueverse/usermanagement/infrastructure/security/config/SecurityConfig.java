package org.vueverse.usermanagement.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenGeneratorFilter;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenValidatorFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JWTTokenGeneratorFilter jwtTokenGeneratorFilter;
    private final JWTTokenValidatorFilter jwtTokenValidatorFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.securityContext(context -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(jwtTokenGeneratorFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/v1/welcome/**").authenticated()
                        .requestMatchers("/api/vi/authentication/**").permitAll());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
