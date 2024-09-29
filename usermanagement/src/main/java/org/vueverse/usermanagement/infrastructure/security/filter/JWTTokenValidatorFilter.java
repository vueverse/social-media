//package org.vueverse.usermanagement.infrastructure.security.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.vueverse.usermanagement.applicatoin.GenerateJwt;
//import org.vueverse.usermanagement.infrastructure.security.service.CustomUserDetailsService;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JWTTokenValidatorFilter extends OncePerRequestFilter {
//    private final GenerateJwt jwtService;
//    private final CustomUserDetailsService userDetailsService;
//    private final HandlerExceptionResolver handlerExceptionResolver;
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        try {
//            final String jwt = authHeader.substring(7);
//            final String userEmail = jwtService.extractUsername(jwt);
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            log.info("{}", authentication);
//            if (userEmail != null && authentication == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//                log.info("{}", userDetails);
//                if (jwtService.isTokenValid(jwt, userDetails)) {
//                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, List.of());
//                    log.info("{}", authToken);
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//
//                }
//            }
//
//            filterChain.doFilter(request, response);
//        } catch (Exception exception) {
//            log.error("", exception);
//            log.debug("{}",  Arrays.toString(exception.getStackTrace()));
//            log.error("{}", exception.getMessage());
//            response.setStatus(500);
//            handlerExceptionResolver.resolveException(request, response, null, exception);
//        }
//    }
//}
//
