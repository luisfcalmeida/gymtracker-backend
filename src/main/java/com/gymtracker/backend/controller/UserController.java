package com.gymtracker.backend.controller;

import com.gymtracker.backend.dto.UserDtos;
import com.gymtracker.backend.entity.User;
import com.gymtracker.backend.mapper.EntityMappers;
import com.gymtracker.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDtos.UserResponse>> getAllUsers() {
        List<UserDtos.UserResponse> users = userService.findAll().stream()
                .map(EntityMappers::toUserResponse)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtos.UserResponse> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(EntityMappers::toUserResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDtos.UserResponse> getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(EntityMappers::toUserResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDtos.UserResponse> createUser(@Valid @RequestBody UserDtos.CreateUserRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setName(request.name());
        User created = userService.save(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(EntityMappers.toUserResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtos.UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDtos.UpdateUserRequest request
    ) {
        return userService.findById(id)
                .map(existing -> {
                    existing.setEmail(request.email());
                    existing.setPassword(request.password());
                    existing.setName(request.name());
                    User saved = userService.save(existing);
                    return ResponseEntity.ok(EntityMappers.toUserResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

