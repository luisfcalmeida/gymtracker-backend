package com.gymtracker.backend.controller;

import com.gymtracker.backend.dto.WorkoutSetDtos;
import com.gymtracker.backend.entity.WorkoutExercise;
import com.gymtracker.backend.entity.WorkoutSet;
import com.gymtracker.backend.mapper.EntityMappers;
import com.gymtracker.backend.exception.NotFoundException;
import com.gymtracker.backend.service.WorkoutExerciseService;
import com.gymtracker.backend.service.WorkoutSetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/workout-sets")
public class WorkoutSetController {

    private final WorkoutSetService workoutSetService;
    private final WorkoutExerciseService workoutExerciseService;

    public WorkoutSetController(WorkoutSetService workoutSetService, WorkoutExerciseService workoutExerciseService) {
        this.workoutSetService = workoutSetService;
        this.workoutExerciseService = workoutExerciseService;
    }

    @GetMapping("/by-workout-exercise/{workoutExerciseId}")
    public ResponseEntity<List<WorkoutSetDtos.WorkoutSetResponse>> getByWorkoutExercise(
            @PathVariable Long workoutExerciseId
    ) {
        List<WorkoutSetDtos.WorkoutSetResponse> list = workoutSetService.findByWorkoutExercise(workoutExerciseId).stream()
                .map(EntityMappers::toWorkoutSetResponse)
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<WorkoutSetDtos.WorkoutSetResponse> createWorkoutSet(
            @Valid @RequestBody WorkoutSetDtos.CreateWorkoutSetRequest request
    ) {
        WorkoutExercise workoutExercise = workoutExerciseService.findById(request.workoutExerciseId())
                .orElseThrow(() -> new NotFoundException("Workout exercise not found with id " + request.workoutExerciseId()));

        WorkoutSet workoutSet = new WorkoutSet();
        workoutSet.setWorkoutExercise(workoutExercise);
        workoutSet.setSetNumber(request.setNumber());
        workoutSet.setRepetitions(request.repetitions());
        workoutSet.setWeight(request.weight());
        workoutSet.setRestSeconds(request.restSeconds());

        WorkoutSet created = workoutSetService.save(workoutSet);
        return ResponseEntity
                .created(URI.create("/api/workout-sets/by-workout-exercise/" + created.getWorkoutExercise().getId()))
                .body(EntityMappers.toWorkoutSetResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutSetDtos.WorkoutSetResponse> updateWorkoutSet(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutSetDtos.UpdateWorkoutSetRequest request
    ) {
        return workoutSetService.findById(id)
                .map(existing -> {
                    existing.setSetNumber(request.setNumber());
                    existing.setRepetitions(request.repetitions());
                    existing.setWeight(request.weight());
                    existing.setRestSeconds(request.restSeconds());
                    WorkoutSet saved = workoutSetService.save(existing);
                    return ResponseEntity.ok(EntityMappers.toWorkoutSetResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutSet(@PathVariable Long id) {
        if (workoutSetService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        workoutSetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-workout-exercise/{workoutExerciseId}")
    public ResponseEntity<Void> deleteByWorkoutExercise(@PathVariable Long workoutExerciseId) {
        workoutSetService.deleteAllByWorkoutExercise(workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}

