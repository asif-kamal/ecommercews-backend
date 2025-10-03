package com.electronics.pgdata.auth.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.repository.AccountUserDetailRepository;
import com.electronics.pgdata.service.AuthorityService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    @Autowired
    private AccountUserDetailRepository accountUserDetailRepository;

    @Autowired
    private AuthorityService authorityService;

    /**
     * Find user by email
     */
    public AccountUser findUserByEmail(String email) {
        return accountUserDetailRepository.findByEmail(email);
    }

    /**
     * Create new user or return existing user
     * This method handles both scenarios in one call
     */
    @Transactional
    public AccountUser createOrUpdateUser(OAuth2User principal, String provider) {
        String email = principal.getAttribute("email");

        // Check if user already exists
        AccountUser existingUser = accountUserDetailRepository.findByEmail(email);
        if (existingUser != null) {
            System.out.println("Found existing user: " + email);
            return existingUser;
        }

        // Create new user WITHOUT authorities to avoid detached entity error
        System.out.println("Creating new user: " + email);
        AccountUser accountUser = AccountUser.builder()
                .firstName(getAttributeOrDefault(principal, "given_name", ""))
                .lastName(getAttributeOrDefault(principal, "family_name", ""))
                .email(email)
                .provider(provider)
                .enabled(true)
                // Remove this line: .authorities(authorityService.getAccountUserAuthorities())
                .build();

        return accountUserDetailRepository.save(accountUser);
    }

    /**
     * Safely extract attribute with default value
     */
    private String getAttributeOrDefault(OAuth2User principal, String attributeName, String defaultValue) {
        String value = principal.getAttribute(attributeName);
        return value != null ? value : defaultValue;
    }
}