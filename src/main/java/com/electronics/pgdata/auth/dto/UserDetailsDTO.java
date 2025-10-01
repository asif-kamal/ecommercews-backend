package com.electronics.pgdata.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Object authorities;

}
