package com.electronics.pgdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electronics.pgdata.model.Electronic;

public interface ElectronicRepository extends JpaRepository<Electronic, Long> {
    // Add custom queries if needed
}
