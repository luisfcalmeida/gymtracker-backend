package com.gymtracker.backend.service;

import com.gymtracker.backend.entity.WorkoutExercise;
import com.gymtracker.backend.repository.WorkoutExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutExerciseService {

    private final WorkoutExerciseRepository workoutExerciseRepository;

    public WorkoutExerciseService(WorkoutExerciseRepository workoutExerciseRepository) {
        this.workoutExerciseRepository = workoutExerciseRepository;
    }

    public List<WorkoutExercise> findByWorkoutPlan(Long planId) {
        return workoutExerciseRepository.findByWorkoutPlanIdOrderByOrderIndex(planId);
    }

    public List<WorkoutExercise> findByPlanAndExercise(Long planId, Long exerciseId) {
        return workoutExerciseRepository.findByWorkoutPlanIdAndExerciseId(planId, exerciseId);
    }

    public WorkoutExercise save(WorkoutExercise workoutExercise) {
        return workoutExerciseRepository.save(workoutExercise);
    }

    public void deleteByPlan(Long planId) {
        workoutExerciseRepository.deleteByWorkoutPlanId(planId);
    }

}
