package com.electronics.pgdata.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JWTTokenHelper {
    public String getToken(HttpServletRequest request) {
        return null;
    }

    public String getUsernemeFromToken(String authToken) {
    }
}
