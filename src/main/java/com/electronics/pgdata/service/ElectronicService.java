package com.electronics.pgdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    public Optional<Electronic> getElectronicById(String id) {
        return repository.findById(id);
    }

    public List<Electronic> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public Page<Electronic> getAllElectronics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
    
    public Page<Electronic> getByBrand(String brand, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByBrand(brand, pageable);
    }
}
