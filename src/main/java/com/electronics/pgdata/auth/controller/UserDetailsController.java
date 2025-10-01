package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.dto.UserDetailsDTO;
import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserDetailsDTO> getUserProfile(Principal principal) {
        AccountUser user = (AccountUser) userDetailsService.loadUserByUsername(principal.getName());
        if (null == user) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        UserDetailsDTO userDetailsDTO = UserDetailsDTO.builder()
                .uuid(user.getAccountUserUuid())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .authorities(user.getAuthorities().toArray())
                .build();
        return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);
    }
}
