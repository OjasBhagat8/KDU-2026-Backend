package com.example.hospital.controller;


import com.example.hospital.dto.CreateShiftTypeRequest;
import com.example.hospital.service.ShiftTypeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shift-types")
public class ShiftTypeController {

    private final ShiftTypeService service;

    public ShiftTypeController(ShiftTypeService service) {
        this.service = service;
    }

    @PostMapping
    public void create(@RequestBody CreateShiftTypeRequest req) {
        service.create(req);
    }
}
