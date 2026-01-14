package com.example.hospital.repository;



import com.example.hospital.dto.CreateUserRequest;
import com.example.hospital.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(CreateUserRequest req) {
        jdbc.update(
                "INSERT INTO users (username, timezone, tenant_id) VALUES (?, ?, ?)",
                req.getUsername(), req.getTimezone(), req.getTenantId().toString()
        );
    }

    public List<User> findByTenant(UUID tenantId) {
        return jdbc.query(
                "SELECT * FROM users WHERE tenant_id = ?",
                new BeanPropertyRowMapper<>(User.class),
                tenantId.toString()
        );
    }

    public void update(UUID id, String username, String timezone) {
        jdbc.update(
                "UPDATE users SET username=?, timezone=? WHERE id=?",
                username, timezone, id.toString()
        );
    }
}

