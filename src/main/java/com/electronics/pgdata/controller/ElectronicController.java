package com.electronics.pgdata.controller;

import com.electronics.pgdata.model.Electronic;
import com.electronics.pgdata.service.ElectronicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/electronics")
@CrossOrigin(origins = "http://localhost:3000") // Adjust as needed for your frontend
public class ElectronicController {
    private final ElectronicService service;
    
    @Autowired
    public ElectronicController(ElectronicService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<Electronic> getAllElectronics() {
        return service.getAllElectronics();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Electronic> getElectronicById(@PathVariable Long id) {
        return service.getElectronicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}