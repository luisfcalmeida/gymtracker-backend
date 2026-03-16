package com.gymtracker.backend.controller;

import com.gymtracker.backend.dto.WorkoutExerciseDtos;
import com.gymtracker.backend.entity.Exercise;
import com.gymtracker.backend.entity.WorkoutExercise;
import com.gymtracker.backend.entity.WorkoutPlan;
import com.gymtracker.backend.exception.NotFoundException;
import com.gymtracker.backend.mapper.EntityMappers;
import com.gymtracker.backend.service.ExerciseService;
import com.gymtracker.backend.service.WorkoutExerciseService;
import com.gymtracker.backend.service.WorkoutPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/workout-exercises")
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;
    private final WorkoutPlanService workoutPlanService;
    private final ExerciseService exerciseService;

    public WorkoutExerciseController(
            WorkoutExerciseService workoutExerciseService,
            WorkoutPlanService workoutPlanService,
            ExerciseService exerciseService
    ) {
        this.workoutExerciseService = workoutExerciseService;
        this.workoutPlanService = workoutPlanService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/by-plan/{planId}")
    public ResponseEntity<List<WorkoutExerciseDtos.WorkoutExerciseResponse>> getByPlan(@PathVariable Long planId) {
        List<WorkoutExerciseDtos.WorkoutExerciseResponse> list = workoutExerciseService.findByWorkoutPlan(planId).stream()
                .map(EntityMappers::toWorkoutExerciseResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-plan/{planId}/exercise/{exerciseId}")
    public ResponseEntity<List<WorkoutExerciseDtos.WorkoutExerciseResponse>> getByPlanAndExercise(
            @PathVariable Long planId,
            @PathVariable Long exerciseId
    ) {
        List<WorkoutExerciseDtos.WorkoutExerciseResponse> list = workoutExerciseService.findByPlanAndExercise(planId, exerciseId).stream()
                .map(EntityMappers::toWorkoutExerciseResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<WorkoutExerciseDtos.WorkoutExerciseResponse> createWorkoutExercise(
            @Valid @RequestBody WorkoutExerciseDtos.CreateWorkoutExerciseRequest request
    ) {
        WorkoutPlan plan = workoutPlanService.findById(request.workoutPlanId())
                .orElseThrow(() -> new NotFoundException("Workout plan not found with id " + request.workoutPlanId()));
        Exercise exercise = exerciseService.findById(request.exerciseId())
                .orElseThrow(() -> new NotFoundException("Exercise not found with id " + request.exerciseId()));

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setWorkoutPlan(plan);
        workoutExercise.setExercise(exercise);
        workoutExercise.setOrderIndex(request.orderIndex());

        WorkoutExercise created = workoutExerciseService.save(workoutExercise);
        return ResponseEntity
                .created(URI.create("/api/workout-exercises/by-plan/" + created.getWorkoutPlan().getId()))
                .body(EntityMappers.toWorkoutExerciseResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutExerciseDtos.WorkoutExerciseResponse> updateWorkoutExercise(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutExerciseDtos.UpdateWorkoutExerciseRequest request
    ) {
        return workoutExerciseService.findById(id)
                .map(existing -> {
                    existing.setOrderIndex(request.orderIndex());
                    WorkoutExercise saved = workoutExerciseService.save(existing);
                    return ResponseEntity.ok(EntityMappers.toWorkoutExerciseResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/by-plan/{planId}")
    public ResponseEntity<Void> deleteByPlan(@PathVariable Long planId) {
        workoutExerciseService.deleteByPlan(planId);
        return ResponseEntity.noContent().build();
    }
}

