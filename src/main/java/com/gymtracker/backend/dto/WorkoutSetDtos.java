package com.gymtracker.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class WorkoutSetDtos {

    public record WorkoutSetResponse(
            Long id,
            Long workoutExerciseId,
            Integer setNumber,
            Integer repetitions,
            Double weight,
            Integer restSeconds
    ) {
    }

    public record CreateWorkoutSetRequest(
            @NotNull
            Long workoutExerciseId,

            @NotNull
            @Min(1)
            Integer setNumber,

            @NotNull
            @Positive
            Integer repetitions,

            @PositiveOrZero
            Double weight,

            @PositiveOrZero
            Integer restSeconds
    ) {
    }

    public record UpdateWorkoutSetRequest(
            @NotNull
            @Min(1)
            Integer setNumber,

            @NotNull
            @Positive
            Integer repetitions,

            @PositiveOrZero
            Double weight,

            @PositiveOrZero
            Integer restSeconds
    ) {
    }
}

