package org.vueverse.usermanagement.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenGeneratorFilter;
import org.vueverse.usermanagement.infrastructure.security.filter.JWTTokenValidatorFilter;

@Configuration
public class SecurityConfig {

    private static final String REGISTER_URL = "/register";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var csrfTokenRequest = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequest.setCsrfRequestAttributeName("csrfToken");
        httpSecurity.securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(new CorsConfig()))
                .csrf((csrf) -> csrf.csrfTokenRequestHandler(csrfTokenRequest)
                        .ignoringRequestMatchers(REGISTER_URL)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new JWTTokenGeneratorFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests.requestMatchers(REGISTER_URL).permitAll())
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
