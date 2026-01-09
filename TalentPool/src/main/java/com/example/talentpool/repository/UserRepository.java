package com.example.talentpool.repository;

import com.example.talentpool.models.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final Map<String , User> users = new HashMap<>();

    public Optional<User> findByUsername(String username){
        return Optional.ofNullable(users.get(username.toLowerCase().trim()));
    }

    public Collection<User> findAll() {
        return users.values();
    }


    public void save(User user){
        users.put(user.getUsername().toLowerCase().trim() , user);
    }
}
