package com.gymtracker.backend.controller;

import com.gymtracker.backend.dto.WorkoutPlanDtos;
import com.gymtracker.backend.entity.User;
import com.gymtracker.backend.entity.WorkoutPlan;
import com.gymtracker.backend.exception.NotFoundException;
import com.gymtracker.backend.mapper.EntityMappers;
import com.gymtracker.backend.service.UserService;
import com.gymtracker.backend.service.WorkoutPlanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;
    private final UserService userService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService, UserService userService) {
        this.workoutPlanService = workoutPlanService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDtos.WorkoutPlanResponse> getWorkoutPlanById(@PathVariable Long id) {
        return workoutPlanService.findById(id)
                .map(EntityMappers::toWorkoutPlanResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<WorkoutPlanDtos.WorkoutPlanResponse>> getWorkoutPlansByUser(@PathVariable Long userId) {
        List<WorkoutPlanDtos.WorkoutPlanResponse> plans = workoutPlanService.findByUserId(userId).stream()
                .map(EntityMappers::toWorkoutPlanResponse)
                .toList();
        return ResponseEntity.ok(plans);
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanDtos.WorkoutPlanResponse> createWorkoutPlan(
            @Valid @RequestBody WorkoutPlanDtos.CreateWorkoutPlanRequest request
    ) {
        User user = userService.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found with id " + request.userId()));

        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.setUser(user);
        workoutPlan.setName(request.name());

        WorkoutPlan created = workoutPlanService.save(workoutPlan);
        return ResponseEntity
                .created(URI.create("/api/workout-plans/" + created.getId()))
                .body(EntityMappers.toWorkoutPlanResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanDtos.WorkoutPlanResponse> updateWorkoutPlan(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutPlanDtos.UpdateWorkoutPlanRequest request
    ) {
        return workoutPlanService.findById(id)
                .map(existing -> {
                    existing.setName(request.name());
                    WorkoutPlan saved = workoutPlanService.save(existing);
                    return ResponseEntity.ok(EntityMappers.toWorkoutPlanResponse(saved));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable Long id) {
        if (workoutPlanService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        workoutPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

