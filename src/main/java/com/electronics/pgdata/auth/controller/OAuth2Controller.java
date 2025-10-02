package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.config.JWTTokenHelper;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.service.OAuth2Service;
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
    public void oauth2Success(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws IOException {
        String username = principal.getAttribute("email");
        AccountUser user = oAuth2UserService.getUser(username);

        if (null == user) {
            user = oAuth2UserService.createUser(principal, "google");
        }

        String token = jwtTokenHelper.generateToken(user.getUsername());
        response.sendRedirect("http:localhost:3000/oauth2/success?token=" + token);
    }

}
