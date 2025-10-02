package com.electronics.pgdata.auth.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.repository.AccountUserDetailRepository;
import com.electronics.pgdata.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    @Autowired
    AccountUserDetailRepository accountUserDetailRepository;

    @Autowired
    private AuthorityService authorityService;

    public AccountUser getUser(String username) {
        return accountUserDetailRepository.findByEmail(username);
    }

    public AccountUser createUser(OAuth2User principal, String provider) {
        AccountUser accountUser = AccountUser.builder()
                .firstName(principal.getAttribute("given_name"))
                .lastName(principal.getAttribute("family_name"))
                .email(principal.getAttribute("email"))
                .provider(provider)
                .enabled(true)
                .authorities(authorityService.getAccountUserAuthorities())
                .build();
        return accountUserDetailRepository.save(accountUser);
    }
}
