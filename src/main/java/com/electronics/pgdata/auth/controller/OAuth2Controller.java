package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.config.JWTTokenHelper;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.service.OAuth2Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.dialect.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @Autowired
    OAuth2Service oAuth2UserService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @GetMapping("/success")
    public void oauth2Success(@AuthenticationPrincipal OAuth2User principal,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        System.out.println("=== OAuth2 Success Handler Called ===");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Principal: " + (principal != null ? "Present" : "Null"));

        try {
            if (principal == null) {
                System.err.println("No OAuth2 principal found");
                response.sendRedirect("http://localhost:3000/oauth2/callback?error=no_principal");
                return;
            }

            String username = principal.getAttribute("email");
            System.out.println("OAuth2 user email: " + username);

            if (username == null) {
                System.err.println("No email found in OAuth2 principal");
                response.sendRedirect("http://localhost:3000/oauth2/callback?error=no_email");
                return;
            }

            AccountUser user = oAuth2UserService.getUser(username);
            if (user == null) {
                System.out.println("Creating new user for: " + username);
                user = oAuth2UserService.createUser(principal, "google");
            }

            String token = jwtTokenHelper.generateToken(user.getUsername());
            System.out.println("Generated JWT token for user: " + username);

            String redirectUrl = "http://localhost:3000/oauth2/callback?token=" + token;
            System.out.println("Redirecting to: " + redirectUrl);
            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            System.err.println("OAuth2 success handler error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("http://localhost:3000/oauth2/callback?error=server_error&error_description=" + e.getMessage());
        }
    }

    @GetMapping("/failure")
    public void oauth2Failure(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.err.println("=== OAuth2 Failure Handler Called ===");
        System.err.println("Request URL: " + request.getRequestURL());
        response.sendRedirect("http://localhost:3000/oauth2/callback?error=authentication_failed");
    }
}