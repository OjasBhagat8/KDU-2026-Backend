package com.example.hospital.repository;


import com.example.hospital.dto.CreateShiftTypeRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShiftTypeRepository {

    private final JdbcTemplate jdbc;

    public ShiftTypeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(CreateShiftTypeRequest req) {
        jdbc.update(
                "INSERT INTO shift_type (name, description, tenant_id) VALUES (?, ?, ?)",
                req.getName(), req.getDescription(), req.getTenantId().toString()
        );
    }
}
