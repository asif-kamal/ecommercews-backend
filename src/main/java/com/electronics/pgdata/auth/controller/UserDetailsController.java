package com.electronics.pgdata.auth.controller;

import com.electronics.pgdata.auth.dto.UserDetailsDTO;
import com.electronics.pgdata.auth.entity.AccountUser;
import com.electronics.pgdata.auth.repository.AccountUserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserDetailsController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AccountUserDetailRepository accountUserRepository;

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDTO> getUserProfile(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            String username = principal.getName();
            Optional<AccountUser> userOptional = Optional.ofNullable(accountUserRepository.findByEmail(username));

            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            AccountUser user = userOptional.get();

            UserDetailsDTO userDetailsDTO = UserDetailsDTO.builder()
                    .uuid(user.getAccountUserUuid())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .authorities(user.getAuthorities().toArray())
                    .build();

            return new ResponseEntity<>(userDetailsDTO, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("Error getting user profile: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/profile")
    public ResponseEntity<UserDetailsDTO> updateUserProfile(@RequestBody UserDetailsDTO userDetailsDTO, Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            // Get the username from the principal
            String username = principal.getName();

            // Find the user directly from repository instead of UserDetailsService
            Optional<AccountUser> userOptional = accountUserRepository.findByEmail(username);

            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            AccountUser user = userOptional.get();

            // Update user fields - add null checks for safety
            if (userDetailsDTO.getFirstName() != null) {
                user.setFirstName(userDetailsDTO.getFirstName());
            }
            if (userDetailsDTO.getLastName() != null) {
                user.setLastName(userDetailsDTO.getLastName());
            }
            if (userDetailsDTO.getEmail() != null) {
                user.setEmail(userDetailsDTO.getEmail());
            }
            if (userDetailsDTO.getPhone() != null) {
                user.setPhone(userDetailsDTO.getPhone());
            }

            // Save updated user
            AccountUser updatedUser = accountUserRepository.save(user);

            // Return updated user data
            UserDetailsDTO responseDTO = UserDetailsDTO.builder()
                    .uuid(updatedUser.getAccountUserUuid())
                    .firstName(updatedUser.getFirstName())
                    .lastName(updatedUser.getLastName())
                    .email(updatedUser.getEmail())
                    .phone(updatedUser.getPhone())
                    .authorities(updatedUser.getAuthorities().toArray())
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            // Log the actual error for debugging
            System.err.println("Error updating user profile: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
