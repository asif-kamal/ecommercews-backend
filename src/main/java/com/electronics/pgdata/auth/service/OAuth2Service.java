package com.electronics.pgdata.auth.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {
    public AccountUser getUser(String username) {
    }

    public AccountUser createUser(OAuth2User principal, String google) {
    }
}
