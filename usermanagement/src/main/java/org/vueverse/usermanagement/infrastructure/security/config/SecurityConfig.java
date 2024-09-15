package org.vueverse.usermanagement.infrastructure.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var csrfTokenRequest = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequest.setCsrfRequestAttributeName("csrfToken");
        httpSecurity.securityContext(
                        (context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {

                            private static final long MAX_AGE = 3600L;
                            private static final String star = "*";
                            private static final String URL = "http://localhost:8080";

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                var config = new CorsConfiguration();
                                config.setAllowedOrigins(Collections.singletonList(URL));
                                config.setAllowedMethods(Collections.singletonList(star));
                                config.setAllowedHeaders(Collections.singletonList(star));
                                config.setExposedHeaders(Collections.singletonList("Authorization"));
                                config.setAllowCredentials(true);
                                config.setMaxAge(MAX_AGE);
                                return config;
                            }
                        })
                );
        return null ;
    }
}
