package com.electronics.pgdata.auth.handler;

import com.electronics.pgdata.auth.config.JWTTokenHelper;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private OAuth2Service oAuth2UserService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        System.out.println("=== OAuth2 SUCCESS HANDLER CALLED ===");
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Authentication: " + authentication.getClass().getName());

        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            System.out.println("OAuth2 User Email: " + email);
            System.out.println("OAuth2 User Name: " + name);
            System.out.println("OAuth2 User Attributes: " + oAuth2User.getAttributes());

            if (email == null) {
                System.err.println("No email found in OAuth2 user");
                response.sendRedirect("http://localhost:3000/oauth2/callback?error=no_email");
                return;
            }

            // Get or create user
            AccountUser user = oAuth2UserService.getUser(email);
            if (user == null) {
                System.out.println("Creating new user for: " + email);
                user = oAuth2UserService.createUser(oAuth2User, "google");
            } else {
                System.out.println("Found existing user: " + email);
            }

            // Generate JWT token
            String token = jwtTokenHelper.generateToken(user.getUsername());
            System.out.println("Generated JWT token length: " + token.length());

            // Redirect to frontend with token
            String redirectUrl = "http://localhost:3000/oauth2/callback?token=" + token;
            System.out.println("Redirecting to: " + redirectUrl);

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            System.err.println("OAuth2 success handler error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("http://localhost:3000/oauth2/callback?error=handler_error&error_description=" + e.getMessage());
        }
    }
}