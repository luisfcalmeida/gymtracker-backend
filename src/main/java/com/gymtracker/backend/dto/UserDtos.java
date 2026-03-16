package com.gymtracker.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDtos {

    public record UserResponse(
            Long id,
            String email,
            String name
    ) {
    }

    public record CreateUserRequest(
            @NotBlank
            @Email
            String email,

            @NotBlank
            @Size(min = 6, max = 100)
            String password,

            @NotBlank
            @Size(max = 255)
            String name
    ) {
    }

    public record UpdateUserRequest(
            @NotBlank
            @Email
            String email,

            @NotBlank
            @Size(min = 6, max = 100)
            String password,

            @NotBlank
            @Size(max = 255)
            String name
    ) {
    }
}

