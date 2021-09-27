package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepo;

    /**
     * @should save a user to the db
     */
    public Mono<User> saveUser(User user) {
        return userRepo.save(user);
    }

    /**
     * @should return a user if found
     * @should return empty if user not found
     */
    public Mono<User> getUser(String id) {
        return userRepo.findById(UUID.fromString(id));
    }

    /**
     * @should return all found users
     */
    public Flux<User> findAllUsers() {
        return userRepo.findAll();
    }
}
