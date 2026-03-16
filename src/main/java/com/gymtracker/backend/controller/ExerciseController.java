package com.gymtracker.backend.controller;

import com.gymtracker.backend.dto.ExerciseDtos;
import com.gymtracker.backend.entity.Exercise;
import com.gymtracker.backend.mapper.EntityMappers;
import com.gymtracker.backend.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDtos.ExerciseResponse>> getExercises(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String muscleGroup,
            @RequestParam(required = false) String equipment
    ) {
        List<Exercise> exercises;
        if (name != null) {
            exercises = exerciseService.findByName(name);
        } else if (muscleGroup != null && equipment != null) {
            exercises = exerciseService.findByMuscleGroup(muscleGroup).stream()
                    .filter(ex -> equipment.equalsIgnoreCase(ex.getEquipment()))
                    .toList();
        } else if (muscleGroup != null) {
            exercises = exerciseService.findByMuscleGroup(muscleGroup);
        } else if (equipment != null) {
            exercises = exerciseService.findByEquipment(equipment);
        } else {
            exercises = exerciseService.findAll();
        }

        List<ExerciseDtos.ExerciseResponse> response = exercises.stream()
                .map(EntityMappers::toExerciseResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDtos.ExerciseResponse> getExerciseById(@PathVariable Long id) {
        return exerciseService.findById(id)
                .map(EntityMappers::toExerciseResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExerciseDtos.ExerciseResponse> createExercise(
            @Valid @RequestBody ExerciseDtos.CreateExerciseRequest request
    ) {
        Exercise exercise = new Exercise();
        exercise.setName(request.name());
        exercise.setMuscleGroup(request.muscleGroup());
        exercise.setEquipment(request.equipment());
        Exercise created = exerciseService.save(exercise);
        return ResponseEntity
                .created(URI.create("/api/exercises/" + created.getId()))
                .body(EntityMappers.toExerciseResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDtos.ExerciseResponse> updateExercise(
            @PathVariable Long id,
            @Valid @RequestBody ExerciseDtos.UpdateExerciseRequest request
    ) {
        return exerciseService.findById(id)
                .map(existing -> {
                    existing.setName(request.name());
                    existing.setMuscleGroup(request.muscleGroup());
                    existing.setEquipment(request.equipment());
                    Exercise saved = exerciseService.save(existing);
                    return ResponseEntity.ok(EntityMappers.toExerciseResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        if (exerciseService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}