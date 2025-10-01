package com.electronics.pgdata.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Table(name = "AUTH_AUTHORITIES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID authorityUuid;

    private String roleCode;

    @Column(nullable = false)
    private String roleDescription;


    @Override
    public String getAuthority() {
        return roleCode;
    }
}
