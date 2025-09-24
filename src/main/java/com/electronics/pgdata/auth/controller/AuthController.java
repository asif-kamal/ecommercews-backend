package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.dto.AccountUserToken;
import com.electronics.pgdata.auth.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<AccountUserToken> login(@RequestBody LoginRequest loginRequest){

    }
}
