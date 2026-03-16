package com.gymtracker.backend.mapper;

import com.gymtracker.backend.dto.ExerciseDtos;
import com.gymtracker.backend.dto.UserDtos;
import com.gymtracker.backend.dto.WorkoutExerciseDtos;
import com.gymtracker.backend.dto.WorkoutPlanDtos;
import com.gymtracker.backend.dto.WorkoutSetDtos;
import com.gymtracker.backend.entity.*;

public class EntityMappers {

    private EntityMappers() {
    }

    public static UserDtos.UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        return new UserDtos.UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public static ExerciseDtos.ExerciseResponse toExerciseResponse(Exercise exercise) {
        if (exercise == null) {
            return null;
        }
        return new ExerciseDtos.ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscleGroup(),
                exercise.getEquipment()
        );
    }

    public static WorkoutPlanDtos.WorkoutPlanResponse toWorkoutPlanResponse(WorkoutPlan plan) {
        if (plan == null) {
            return null;
        }
        Long userId = plan.getUser() != null ? plan.getUser().getId() : null;
        return new WorkoutPlanDtos.WorkoutPlanResponse(
                plan.getId(),
                userId,
                plan.getName(),
                plan.getCreatedAt()
        );
    }

    public static WorkoutExerciseDtos.WorkoutExerciseResponse toWorkoutExerciseResponse(WorkoutExercise workoutExercise) {
        if (workoutExercise == null) {
            return null;
        }
        Long planId = workoutExercise.getWorkoutPlan() != null ? workoutExercise.getWorkoutPlan().getId() : null;
        Long exerciseId = workoutExercise.getExercise() != null ? workoutExercise.getExercise().getId() : null;
        return new WorkoutExerciseDtos.WorkoutExerciseResponse(
                workoutExercise.getId(),
                planId,
                exerciseId,
                workoutExercise.getOrderIndex()
        );
    }

    public static WorkoutSetDtos.WorkoutSetResponse toWorkoutSetResponse(WorkoutSet set) {
        if (set == null) {
            return null;
        }
        Long workoutExerciseId = set.getWorkoutExercise() != null ? set.getWorkoutExercise().getId() : null;
        return new WorkoutSetDtos.WorkoutSetResponse(
                set.getId(),
                workoutExerciseId,
                set.getSetNumber(),
                set.getRepetitions(),
                set.getWeight(),
                set.getRestSeconds()
        );
    }
}

