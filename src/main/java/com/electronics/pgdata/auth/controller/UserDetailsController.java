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
            System.out.println("Getting profile for username: " + username);

            AccountUser user = accountUserRepository.findByEmail(username);
            if (user == null) {
                System.out.println("User not found for email: " + username);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            UserDetailsDTO userDetailsDTO = UserDetailsDTO.builder()
                    .uuid(user.getAccountUserUuid())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .authorities(user.getAuthorities() != null ? user.getAuthorities().toArray() : new Object[0])
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
            String username = principal.getName();
            System.out.println("Updating profile for username: " + username);
            System.out.println("Update data: " + userDetailsDTO);

            AccountUser user = accountUserRepository.findByEmail(username);
            if (user == null) {
                System.out.println("User not found for email: " + username);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update user fields - add null checks for safety
            if (userDetailsDTO.getFirstName() != null && !userDetailsDTO.getFirstName().trim().isEmpty()) {
                user.setFirstName(userDetailsDTO.getFirstName().trim());
            }
            if (userDetailsDTO.getLastName() != null && !userDetailsDTO.getLastName().trim().isEmpty()) {
                user.setLastName(userDetailsDTO.getLastName().trim());
            }
            // Don't update email to avoid conflicts with authentication
            if (userDetailsDTO.getPhone() != null) {
                user.setPhone(userDetailsDTO.getPhone().trim());
            }

            System.out.println("Saving user: " + user.getEmail());

            // Save updated user
            AccountUser updatedUser = accountUserRepository.save(user);

            System.out.println("User saved successfully");

            // Return updated user data
            UserDetailsDTO responseDTO = UserDetailsDTO.builder()
                    .uuid(updatedUser.getAccountUserUuid())
                    .firstName(updatedUser.getFirstName())
                    .lastName(updatedUser.getLastName())
                    .email(updatedUser.getEmail())
                    .phone(updatedUser.getPhone())
                    .authorities(updatedUser.getAuthorities() != null ? updatedUser.getAuthorities().toArray() : new Object[0])
                    .build();

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
