package com.example.hospital.service;


import com.example.hospital.dto.CreateUserRequest;
import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public void create(CreateUserRequest req) {
        repo.insert(req);
    }

    public List<User> getByTenant(UUID tenantId) {
        return repo.findByTenant(tenantId);
    }

    public void update(UUID id, String username, String tz) {
        repo.update(id, username, tz);
    }
}
