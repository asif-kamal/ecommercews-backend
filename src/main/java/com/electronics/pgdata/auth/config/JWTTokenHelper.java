package com.electronics.pgdata.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;

@Component
public class JWTTokenHelper {
    @Value("${jwt.auth.app}")
    private String APP_NAME;

    @Value("${jwt.auth.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.auth.expires_in}")
    private int EXPIRES_IN;

    public String generateToken(String username) {
        return Jwts.builder()
                .issuer(APP_NAME)
                .subject(username)
                .issuedAt(Calendar.getInstance().getTime())
                .expiration(generateExpirationDate())
                .signWith(getSigningKey())
                .compact();

    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN * 1000L);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpirationDate(String token) {
        Date expirationDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromRequest(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return authHeader;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    private String getAuthHeaderFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getUsernameFromToken(String authToken) {

        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(authToken);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getAllClaimsFromToken(String authToken) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(authToken)
                    .getPayload();

        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
