package com.electronics.pgdata.auth.service;

import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.repository.AccountUserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAccountUserDetailService implements UserDetailsService {

    @Autowired
    private AccountUserDetailRepository accountUserDetailRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser aUser = accountUserDetailRepository.findByEmail(username);
        if (aUser == null) {
            throw new UsernameNotFoundException("User not found by this username: " + username);
        }
        return aUser;
    }
}
