package com.electronics.pgdata.service;

import com.electronics.pgdata.auth.entity.Authority;
import com.electronics.pgdata.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<Authority> getAccountUserAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        Authority authority = authorityRepository.findByRoleCode("ROLE_USER");
        authorities.add(authority);
        return authorities;
    }

    public Authority createAuthority(String roleCode, String description) {
        Authority authority = Authority.builder().roleCode(roleCode).roleDescription(description).build();
        return authorityRepository.save(authority);
    }
}
