package com.gymtracker.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseDtos {

    public record ExerciseResponse(
            Long id,
            String name,
            String muscleGroup,
            String equipment
    ) {
    }

    public record CreateExerciseRequest(
            @NotBlank
            @Size(max = 255)
            String name,

            @Size(max = 100)
            String muscleGroup,

            @Size(max = 100)
            String equipment
    ) {
    }

    public record UpdateExerciseRequest(
            @NotBlank
            @Size(max = 255)
            String name,

            @Size(max = 100)
            String muscleGroup,

            @Size(max = 100)
            String equipment
    ) {
    }
}

