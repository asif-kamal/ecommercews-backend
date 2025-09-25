package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.dto.AccountUserToken;
import com.electronics.pgdata.auth.dto.LoginRequest;
import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AccountUserToken> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(),
                    loginRequest.getPassword());
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if (authenticationResponse.isAuthenticated()) {
                AccountUser accountUser = (AccountUser) authenticationResponse.getPrincipal();
                if (accountUser.isEnabled()) {
                    String token = "";
                    AccountUserToken accountUserToken = AccountUserToken.builder().token(token).build();
                    return ResponseEntity.ok(accountUserToken);
                }
            }
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
