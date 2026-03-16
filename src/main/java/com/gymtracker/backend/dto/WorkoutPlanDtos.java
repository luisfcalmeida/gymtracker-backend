package com.gymtracker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class WorkoutPlanDtos {

    public record WorkoutPlanResponse(
            Long id,
            Long userId,
            String name,
            LocalDateTime createdAt
    ) {
    }

    public record CreateWorkoutPlanRequest(
            @NotNull
            Long userId,

            @NotBlank
            @Size(max = 255)
            String name
    ) {
    }

    public record UpdateWorkoutPlanRequest(
            @NotBlank
            @Size(max = 255)
            String name
    ) {
    }
}

