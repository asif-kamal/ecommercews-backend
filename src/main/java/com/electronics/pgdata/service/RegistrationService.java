package com.electronics.pgdata.service;

import com.electronics.pgdata.auth.dto.RegistrationRequest;
import com.electronics.pgdata.auth.dto.RegistrationResponse;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.helper.VerificationCodeGenerator;
import com.electronics.pgdata.auth.repository.AccountUserDetailRepository;
import com.electronics.pgdata.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private AccountUserDetailRepository accountUserDetailRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegistrationResponse createAccountUser(RegistrationRequest registerRequest) {

        AccountUser existingUser = accountUserDetailRepository.findByEmail(registerRequest.getEmail());

        if (existingUser != null) {
            return RegistrationResponse.builder().code("409")
                    .message("User already exists with this email: " + registerRequest.getEmail()).build();
        }

        try {
            AccountUser accountUser = new AccountUser();
            accountUser.setFirstName(registerRequest.getFirstName());
            accountUser.setLastName(registerRequest.getLastName());
            accountUser.setEmail(registerRequest.getEmail());
            accountUser.setEnabled(false);
            accountUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            accountUser.setProvider("manual");

            String code = VerificationCodeGenerator.generateVerificationCode();
            accountUser.setVerificationCode(code);

            accountUser.setAuthorities(authorityService.getAccountUserAuthorities());
            accountUserDetailRepository.save(accountUser);
        } catch (Exception ex) {

        }
        return null;
    }
}
