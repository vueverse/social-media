package org.vueverse.usermanagement.infrastructure.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vueverse.usermanagement.applicatoin.GenerateJwt;
import org.vueverse.usermanagement.infrastructure.security.SecurityConstants;
import org.vueverse.usermanagement.infrastructure.security.service.CustomUserDetailsService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {


    private final GenerateJwt jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            var userDetails = userDetailsService.loadUserByUsername(principal.getUsername());
            String jwt = jwtService.generateToken(userDetails);
            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        }

        filterChain.doFilter(request, response);
    }

}
