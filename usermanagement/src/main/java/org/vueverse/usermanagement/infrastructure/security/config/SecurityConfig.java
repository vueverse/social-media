package org.vueverse.usermanagement.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.vueverse.usermanagement.infrastructure.security.filter.CsrfCookieFilter;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenGeneratorFilter;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenValidatorFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JWTTokenGeneratorFilter jwtTokenGeneratorFilter;
    private final JWTTokenValidatorFilter jwtTokenValidatorFilter;
    private final CsrfCookieFilter csrfCookieFilter;
    private static final String REGISTER_URL = "/api/vi/authentication/register";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var csrfTokenRequest = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequest.setCsrfRequestAttributeName("csrfToken");
        httpSecurity.securityContext(context -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(new CorsConfig()))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfTokenRequest)
                        .ignoringRequestMatchers(REGISTER_URL)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(csrfCookieFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtTokenGeneratorFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests.requestMatchers("/api/vi/authentication/**").permitAll());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
