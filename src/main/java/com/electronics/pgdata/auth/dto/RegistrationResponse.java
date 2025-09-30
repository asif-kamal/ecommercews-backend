package com.electronics.pgdata.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private CharSequence password;
    private int code;
    private String message;
}
