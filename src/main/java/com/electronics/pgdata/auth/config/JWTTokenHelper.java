package com.electronics.pgdata.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenHelper {
    @Value("${jwt.auth.app}")
    private String APP_NAME;

    @Value("${jwt.auth.secret_key}")
    private String SECRET_KEY;

    @Value("${jwt.auth.expires_in}")
    private int EXPIRES_IN;

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
