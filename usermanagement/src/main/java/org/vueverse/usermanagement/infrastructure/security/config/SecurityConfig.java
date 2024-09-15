package org.vueverse.usermanagement.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var csrfTokenRequest = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequest.setCsrfRequestAttributeName("csrfToken");
        httpSecurity.securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(SecurityConfig::getHttpSecurityCorsConfigurer);
        return null;
    }

    private static void getHttpSecurityCorsConfigurer(CorsConfigurer<HttpSecurity> cors) {
        cors.configurationSource(new CorsConfig());
    }
}
