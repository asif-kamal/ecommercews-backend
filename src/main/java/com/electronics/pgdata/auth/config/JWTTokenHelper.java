package com.electronics.pgdata.auth.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
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
                .expiration(getExpirationDate())
                .signWith(getSigningKey())
                .compact();

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExpirationDate() {
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromRequest(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String getAuthHeaderFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getUsernameFromToken(String authToken) {
        return null;
    }
}
