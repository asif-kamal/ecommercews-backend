package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        AccountUser user = (AccountUser) userDetailsService.loadUserByUsername(principal.getName());
        if (null == user) {

        }
    }
}
