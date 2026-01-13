package com.example.hospital.service;


import com.example.hospital.dto.CreateShiftTypeRequest;
import com.example.hospital.repository.ShiftTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ShiftTypeService {

    private final ShiftTypeRepository repo;

    public ShiftTypeService(ShiftTypeRepository repo) {
        this.repo = repo;
    }

    public void create(CreateShiftTypeRequest req) {
        repo.insert(req);
    }
}

