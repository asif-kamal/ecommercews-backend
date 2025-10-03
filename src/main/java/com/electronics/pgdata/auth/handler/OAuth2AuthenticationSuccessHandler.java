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
    private OAuth2Service oAuth2Service;

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

            System.out.println("OAuth2 User Email: " + email);
            System.out.println("OAuth2 User Attributes: " + oAuth2User.getAttributes());

            if (email == null) {
                System.err.println("No email found in OAuth2 user");
                response.sendRedirect("http://localhost:3000/oauth2/callback?error=no_email");
                return;
            }

            // This handles both new and existing users
            AccountUser user = oAuth2Service.createOrUpdateUser(oAuth2User, "google");
            System.out.println("User processed: " + email);

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
            response.sendRedirect("http://localhost:3000/oauth2/callback?error=handler_error");
        }
    }
}