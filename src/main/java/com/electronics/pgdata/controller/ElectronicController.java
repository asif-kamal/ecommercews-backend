package com.electronics.pgdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electronics.pgdata.model.Electronic;
import com.electronics.pgdata.service.ElectronicService;

@RestController
@RequestMapping("/api/electronics")
@CrossOrigin(origins = "http://localhost:3000") // Adjust as needed for your frontend
public class ElectronicController {
    private final ElectronicService service;
    
    @Autowired
    public ElectronicController(ElectronicService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Electronic> getElectronicById(@PathVariable String id) {
        return service.getElectronicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Electronic> getByCategory(@PathVariable String category) {
        return service.findByCategory(category);
    }

    @GetMapping
    public ResponseEntity<Page<Electronic>> getAllElectronics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllElectronics(page, size));
    }
    
    @GetMapping("/brand/{brand}")
    public ResponseEntity<Page<Electronic>> getByBrand(
            @PathVariable String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getByBrand(brand, page, size));
    }
}