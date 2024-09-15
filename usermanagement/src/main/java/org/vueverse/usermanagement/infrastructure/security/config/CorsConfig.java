package org.vueverse.usermanagement.infrastructure.security.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

public class CorsConfig implements CorsConfigurationSource {
    private static final long MAX_AGE = 3600L;
    private static final String star = "*";
    private static final String URL = "http://localhost:8080";

    @Override
    public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList(URL));
        config.setAllowedMethods(Collections.singletonList(star));
        config.setAllowedHeaders(Collections.singletonList(star));
        config.setExposedHeaders(Collections.singletonList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(MAX_AGE);
        return config;
    }
}

