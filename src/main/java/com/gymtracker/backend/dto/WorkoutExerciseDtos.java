package com.gymtracker.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WorkoutExerciseDtos {

    public record WorkoutExerciseResponse(
            Long id,
            Long workoutPlanId,
            Long exerciseId,
            Integer orderIndex
    ) {
    }

    public record CreateWorkoutExerciseRequest(
            @NotNull
            Long workoutPlanId,

            @NotNull
            Long exerciseId,

            @NotNull
            @Min(0)
            Integer orderIndex
    ) {
    }

    public record UpdateWorkoutExerciseRequest(
            @NotNull
            @Min(0)
            Integer orderIndex
    ) {
    }
}

