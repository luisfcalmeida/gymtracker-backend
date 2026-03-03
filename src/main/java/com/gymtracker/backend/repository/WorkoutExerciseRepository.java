package com.gymtracker.backend.repository;

import com.gymtracker.backend.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {

    List<WorkoutExercise> findByWorkoutPlanIdOrderByOrderIndex(Long workoutPlanId);

    List<WorkoutExercise> findByWorkoutPlanIdAndExerciseId(Long planId, Long exerciseId);

    void deleteByWorkoutPlanId(Long planId);
}
