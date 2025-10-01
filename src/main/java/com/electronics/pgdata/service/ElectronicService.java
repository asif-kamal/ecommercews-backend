package com.electronics.pgdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.electronics.pgdata.model.Electronic;
import com.electronics.pgdata.repository.ElectronicRepository;

@Service
public class ElectronicService {

    private final ElectronicRepository repository;

    public ElectronicService(ElectronicRepository repository) {
        this.repository = repository;
    }

    public Page<Electronic> getRandomElectronics(int page, int size) {
        List<Electronic> randomItems = repository.findRandomElectronics(size);
        return new PageImpl<>(randomItems, PageRequest.of(page, size), repository.count());
    }

    public Page<Electronic> searchElectronics(String query, int page, int size) {
        // Add null/empty check
        if (query == null || query.trim().isEmpty()) {
            return getAllElectronics(page, size);
        }

        System.out.println("Searching for: " + query + " on page " + page);

        Pageable pageable = PageRequest.of(page, size);
        Page<Electronic> results = repository.searchByNameOrDescriptionOrBrand(query.trim(), pageable);

        System.out.println("Found " + results.getTotalElements() + " results");

        return results;
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
