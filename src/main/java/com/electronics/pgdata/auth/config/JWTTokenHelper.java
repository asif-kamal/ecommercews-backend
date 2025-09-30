package com.electronics.pgdata.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenHelper {
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
