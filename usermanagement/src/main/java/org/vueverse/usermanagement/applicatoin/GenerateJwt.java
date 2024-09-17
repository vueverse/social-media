package org.vueverse.usermanagement.applicatoin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vueverse.usermanagement.infrastructure.security.SecurityConstants;
import org.vueverse.usermanagement.infrastructure.security.UserContextModel;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.vueverse.usermanagement.infrastructure.security.SecurityConstants.JWT_EXPIRATION;
import static org.vueverse.usermanagement.infrastructure.security.SecurityConstants.JWT_KEY;

@Service
public class GenerateJwt {
    private static final String CLAIM_USER_ID = "userId";


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraAllClaim(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails user) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        if (user instanceof UserContextModel userContextModel) {
            extraClaims.put(CLAIM_USER_ID, userContextModel.getUserId());
        }

        return generateToken(extraClaims, user);
    }

    public long getExpirationTime() {
        return JWT_EXPIRATION;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
                .signWith(getSignInKey(), Jwts.SIG.HS512)
                .compact();
    }

    private Claims extraAllClaim(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
