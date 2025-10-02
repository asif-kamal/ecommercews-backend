package com.electronics.pgdata.auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @GetMapping("/success")
    public void oauth2Success(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) {
        String username = principal.getAttribute("email");
}
