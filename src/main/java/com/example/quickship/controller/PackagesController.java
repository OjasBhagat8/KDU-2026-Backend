package com.example.quickship.controller;

import com.example.quickship.DTO.PackageRequestDTO;
import com.example.quickship.model.Package;
import com.example.quickship.service.PackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackagesController {

    private final PackageService service;

    public PackagesController(PackageService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Package> addPackage(@RequestBody PackageRequestDTO newPackage) {
        Package saved = service.addPackage(newPackage);
        return ResponseEntity.accepted().body(saved);
    }
    @GetMapping
    public List<Package> getPackages(){
        return service.getAll();
    }
}
