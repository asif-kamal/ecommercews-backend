package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.config.JWTTokenHelper;
import com.electronics.pgdata.auth.dto.AccountUserToken;
import com.electronics.pgdata.auth.dto.LoginRequest;
import com.electronics.pgdata.auth.dto.RegistrationRequest;
import com.electronics.pgdata.auth.dto.RegistrationResponse;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTTokenHelper jwtTokenHelper;

    @PostMapping("/login")
    public ResponseEntity<AccountUserToken> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(),
                    loginRequest.getPassword());
            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);


            if (authenticationResponse.isAuthenticated()) {
                AccountUser accountUser = (AccountUser) authenticationResponse.getPrincipal();
                if (accountUser.isEnabled()) {


                    String token = jwtTokenHelper.generateToken(accountUser.getEmail());
                    AccountUserToken accountUserToken = AccountUserToken.builder().token(token).build();
                    return ResponseEntity.ok(accountUserToken);
                }
            }
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registerRequest){
        RegistrationResponse registrationResponse = registrationService.createAccountUser(registerRequest);
        return new ResponseEntity<>(registrationResponse, registrationResponse.getCode() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String code = map.get("code");
        AccountUser accountUser = (AccountUser) userDetailsService.loadUserByUsername(username);

        if (accountUser != null && accountUser.getVerificationCode().equals(code)) {
            registrationService.verifyAccount(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
