package com.electronics.pgdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronics.pgdata.model.Electronic;
import com.electronics.pgdata.repository.ElectronicRepository;

@Service
public class ElectronicService {
    private final ElectronicRepository repository;
    
    @Autowired
    public ElectronicService(ElectronicRepository repository) {
        this.repository = repository;
    }
    
    public List<Electronic> getAllElectronics() {
        return repository.findAll();
    }
    
    public Optional<Electronic> getElectronicById(Long id) {
        return repository.findById(id);
    }
}
